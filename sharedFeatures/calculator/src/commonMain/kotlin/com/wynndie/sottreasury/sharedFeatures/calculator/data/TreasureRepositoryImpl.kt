package com.wynndie.sottreasury.sharedFeatures.calculator.data

import com.wynndie.sottreasury.sharedCore.data.remote.safeCall
import com.wynndie.sottreasury.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasury.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasury.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasury.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasury.sharedCore.domain.outcome.map
import com.wynndie.sottreasury.sharedFeatures.calculator.data.dto.ResponseDto
import com.wynndie.sottreasury.sharedFeatures.calculator.data.dto.TreasureValueDto
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.dao.TreasureDao
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CurrencyEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.EmissaryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.FactionEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.SubcategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.VariantEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories.TreasureRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TreasureRepositoryImpl(
    private val httpClient: HttpClient,
    private val treasureDao: TreasureDao
) : TreasureRepository {
    override suspend fun syncData(): EmptyOutcome<DataError.Remote> = withContext(Dispatchers.IO) {
        val syncResult = coroutineScope {
            val treasure = fetchSheet("Treasure!A:G") { it.toTreasureEntities() }
            val treasureValues = fetchSheet("Treasure_Values!A:D") { it.toTreasureValueDtoList() }
            val factions = fetchSheet("Factions!A:C") { it.toFactionEntities() }
            val categories = fetchSheet("Categories!A:C") { it.toCategoryEntities() }
            val subcategories = fetchSheet("Subcategories!A:C") { it.toSubcategoryEntities() }
            val variants = fetchSheet("Variants!A:C") { it.toVariantEntities() }
            val currencies = fetchSheet("Currencies!A:D") { it.toCurrencyEntities() }
            val emissaries = fetchSheet("Emissaries!A:E") { it.toEmissaryEntityList() }

            SyncResults(
                treasure = treasure.await(),
                treasureValues = treasureValues.await(),
                factions = factions.await(),
                categories = categories.await(),
                subcategories = subcategories.await(),
                variants = variants.await(),
                currencies = currencies.await(),
                emissaries = emissaries.await()
            )
        }

        val treasureEntities = syncResult.treasure
            .getOrElse { return@withContext Outcome.Error(it) }
        val treasureValuesDto = syncResult.treasureValues
            .getOrElse { return@withContext Outcome.Error(it) }
        val factionEntities = syncResult.factions
            .getOrElse { return@withContext Outcome.Error(it) }
        val categoryEntities = syncResult.categories
            .getOrElse { return@withContext Outcome.Error(it) }
        val subcategoryEntities = syncResult.subcategories
            .getOrElse { return@withContext Outcome.Error(it) }
        val variantEntities = syncResult.variants
            .getOrElse { return@withContext Outcome.Error(it) }
        val currencyEntities = syncResult.currencies
            .getOrElse { return@withContext Outcome.Error(it) }
        val emissaryEntities = syncResult.emissaries
            .getOrElse { return@withContext Outcome.Error(it) }


        coroutineScope {
            launch { treasureDao.insertTreasure(treasureEntities) }
            launch { treasureDao.insertFactions(factionEntities) }
            launch { treasureDao.insertCategories(categoryEntities) }
            launch { treasureDao.insertSubcategories(subcategoryEntities) }
            launch { treasureDao.insertVariants(variantEntities) }
            launch { treasureDao.insertCurrencies(currencyEntities) }
            launch { treasureDao.insertEmissaries(emissaryEntities) }
            launch {
                val treasureValuesById = treasureValuesDto.groupBy { it.treasureId }
                val currencyMap = currencyEntities.associateBy { it.id }
                treasureEntities.flatMap { treasureEntity ->
                    treasureValuesById[treasureEntity.id]?.mapNotNull {
                        it.toEntity(treasureEntity.id, currencyMap)
                    } ?: emptyList()
                }.also { treasureDao.insertValues(it) }
            }
        }.join()

        return@withContext Outcome.Success(Unit)
    }


    override fun getTreasure(): Flow<Map<Int, Faction>> {
        return combine(
            treasureDao.getAllTreasure(),
            treasureDao.getAllFactions(),
            treasureDao.getAllCategories(),
            treasureDao.getAllSubcategory(),
            treasureDao.getAllVariants()
        ) { allTreasure, allFactions, allCategories, allSubcategories, allVariants ->

            val factionsTree =
                mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Treasure>>>>>()

            val treasureFactions = allTreasure.associate { treasureWithValues ->
                val treasureId = treasureWithValues.treasure.id
                val factions = treasureWithValues.treasure.factions.split(",").map { it.toInt() }
                treasureId to factions
            }

            allTreasure.forEach { treasureWithValues ->
                val treasureEntity = treasureWithValues.treasure
                treasureFactions[treasureEntity.id]?.forEach { factionId ->
                    factionsTree.getOrPut(factionId) { mutableMapOf() }
                        .getOrPut(treasureEntity.category.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.subcategory.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.variant.toInt()) { mutableMapOf() }[treasureEntity.id] =
                        treasureWithValues.toDomain()
                }
            }

            val categories = allCategories.associateBy { it.id }
            val subcategories = allSubcategories.associateBy { it.id }
            val variants = allVariants.associateBy { it.id }
            allFactions.mapNotNull { faction ->
                val categoriesTree = factionsTree[faction.id] ?: return@mapNotNull null
                faction.toDomain(categoriesTree, categories, subcategories, variants)
            }.associateBy { it.id }
        }
    }

    override fun getCurrencies(): Flow<List<TreasureValue>> {
        return treasureDao.getAllCurrencies().map { currencies ->
            val result = currencies
                .sortedBy { it.sortOrder }
                .map { currency -> currency.toDomain() }
            println("PRINTLINE: result $result")
            result
        }
    }

    override fun getEmissaries(): Flow<List<Emissary>> {
        return treasureDao.getAllEmissaries().map {
            it.map { emissaries -> emissaries.toDomain() }
        }
    }


    private inline fun <reified T> CoroutineScope.fetchSheet(
        sheetName: String,
        crossinline mapper: (ResponseDto) -> T
    ): Deferred<Outcome<T, DataError.Remote>> {
        return async {
            safeCall<ResponseDto> {
                httpClient.get("$BASE_URL/$SHEET_ID_PROD/values/$sheetName") {
                    parameter("key", API_KEY)
                }
            }.map { mapper(it) }
        }
    }


    @Suppress("SpellCheckingInspection")
    companion object {
        private const val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets"
        private const val API_KEY = "AIzaSyBlAu-CRXh6hnsioN-wf7WB3BIYr3Dr24c"

        private const val SHEET_ID_PROD = "1b3DQ7vPqF8EQq7lviwipRljqYzbyjXfRGJPn3ucc-ss"
        private const val SHEET_ID_TEST = "1nu-iEo2SQIs4L-tYZno9YeGqg_YW1_j2kyFSTWyHpHs"

        private data class SyncResults(
            val treasure: Outcome<List<TreasureEntity>, DataError.Remote>,
            val treasureValues: Outcome<List<TreasureValueDto>, DataError.Remote>,
            val factions: Outcome<List<FactionEntity>, DataError.Remote>,
            val categories: Outcome<List<CategoryEntity>, DataError.Remote>,
            val subcategories: Outcome<List<SubcategoryEntity>, DataError.Remote>,
            val variants: Outcome<List<VariantEntity>, DataError.Remote>,
            val currencies: Outcome<List<CurrencyEntity>, DataError.Remote>,
            val emissaries: Outcome<List<EmissaryEntity>, DataError.Remote>
        )
    }
}