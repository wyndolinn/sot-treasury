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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class TreasureRepositoryImpl(
    private val httpClient: HttpClient,
    private val treasureDao: TreasureDao
) : TreasureRepository {
    override suspend fun syncTreasure(): EmptyOutcome<DataError.Remote> {

        val syncResult = coroutineScope {
            val treasure = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Treasure!A:G") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toTreasureEntities() }
            }
            val treasureValues = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Treasure_Values!A:D") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toTreasureValueDtoList() }
            }
            val factions = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Factions!A:C") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toFactionEntities() }
            }
            val categories = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Categories!A:C") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toCategoryEntities() }
            }
            val subcategories = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Subcategories!A:C") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toSubcategoryEntities() }
            }
            val variants = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Variants!A:C") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toVariantEntities() }
            }
            val currencies = async {
                safeCall<ResponseDto> {
                    httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Currencies!A:C") {
                        parameter("key", API_KEY)
                    }
                }.map { it.toCurrencyEntities() }
            }

            SyncResults(
                treasure = treasure.await(),
                treasureValues = treasureValues.await(),
                factions = factions.await(),
                categories = categories.await(),
                subcategories = subcategories.await(),
                variants = variants.await(),
                currencies = currencies.await()
            )
        }

        val treasureEntities = syncResult.treasure.getOrElse { return Outcome.Error(it) }
        val valuesByTreasureId = syncResult.treasureValues.getOrElse { return Outcome.Error(it) }
        val factionEntities = syncResult.factions.getOrElse { return Outcome.Error(it) }
        val categoryEntities = syncResult.categories.getOrElse { return Outcome.Error(it) }
        val subcategoryEntities = syncResult.subcategories.getOrElse { return Outcome.Error(it) }
        val variantEntities = syncResult.variants.getOrElse { return Outcome.Error(it) }
        val currencyEntities = syncResult.currencies.getOrElse { return Outcome.Error(it) }

        treasureDao.insertTreasure(treasureEntities)
        treasureDao.insertFactions(factionEntities)
        treasureDao.insertCategories(categoryEntities)
        treasureDao.insertSubcategories(subcategoryEntities)
        treasureDao.insertVariants(variantEntities)
        treasureDao.insertCurrencies(currencyEntities)


        treasureEntities.forEach { treasureDto ->
            val treasureValues = valuesByTreasureId.groupBy { it.treasureId }[treasureDto.id]
                ?.mapNotNull {
                    it.toEntity(
                        treasureId = treasureDto.id,
                        currencies = currencyEntities.associateBy { entity -> entity.id }
                    )
                }
                ?: emptyList()
            treasureDao.insertValues(treasureValues)
        }

        treasureDao.insertTreasure(treasureEntities)
        treasureDao.insertFactions(factionEntities)
        treasureDao.insertCategories(categoryEntities)
        treasureDao.insertSubcategories(subcategoryEntities)
        treasureDao.insertVariants(variantEntities)
        treasureDao.insertCurrencies(currencyEntities)

        treasureEntities.forEach { treasureDto ->
            val treasureValues = valuesByTreasureId.groupBy { it.treasureId }[treasureDto.id]
                ?.mapNotNull {
                    it.toEntity(
                        treasureId = treasureDto.id,
                        currencies = currencyEntities.associateBy { entity -> entity.id }
                    )
                }
                ?: emptyList()
            treasureDao.insertValues(treasureValues)
        }

        return Outcome.Success(Unit)
    }


    override suspend fun syncEmissaries(): EmptyOutcome<DataError.Remote> {
        val emissaryDtoList = safeCall<ResponseDto> {
            httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Emissaries!A:E") {
                parameter("key", API_KEY)
            }
        }.map { it.toEmissaryEntityList() }.getOrElse { return Outcome.Error(it) }

        treasureDao.insertEmissaries(emissaryDtoList)
        return Outcome.Success(Unit)
    }


    override fun getTreasure(): Flow<List<Faction>> {
        return combine(
            treasureDao.getAllTreasure(),
            treasureDao.getAllFactions(),
            treasureDao.getAllCategories(),
            treasureDao.getAllSubcategory(),
            treasureDao.getAllVariants()
        ) { allTreasure, allFactions, allCategories, allSubcategories, allVariants ->

            val tree =
                mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableList<Treasure>>>>>()
            allTreasure.forEach { treasureWithValues ->
                val treasureEntity = treasureWithValues.treasure
                val treasure = treasureWithValues.toDomain()
                treasureEntity.factions.split(",").forEach { factionId ->
                    tree
                        .getOrPut(factionId.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.category.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.subcategory.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.variant.toInt()) { mutableListOf() }
                        .add(treasure)
                }
            }

            allFactions.mapNotNull { faction ->
                val categoriesTree = tree[faction.id] ?: return@mapNotNull null
                faction.toDomain(categoriesTree, allCategories, allSubcategories, allVariants)
            }
        }
    }

    override fun getCurrencies(): Flow<List<TreasureValue>> {
        return treasureDao.getAllCurrencies().map {
            it.map { currencies -> currencies.toDomain() }
        }
    }

    override fun getEmissaries(): Flow<List<Emissary>> {
        return treasureDao.getAllEmissaries().map {
            it.map { emissaries -> emissaries.toDomain() }
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
            val currencies: Outcome<List<CurrencyEntity>, DataError.Remote>
        )
    }
}