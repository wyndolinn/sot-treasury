package com.wynndie.sottreasury.sharedFeatures.calculator.data

import com.wynndie.sottreasury.sharedCore.data.remote.safeCall
import com.wynndie.sottreasury.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasury.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasury.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasury.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasury.sharedCore.domain.outcome.map
import com.wynndie.sottreasury.sharedFeatures.calculator.data.dto.ResponseDto
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.dao.TreasureDao
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories.TreasureRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class TreasureRepositoryImpl(
    private val httpClient: HttpClient,
    private val treasureDao: TreasureDao
) : TreasureRepository {
    override suspend fun syncTreasure(): EmptyOutcome<DataError.Remote> {

        val deferredResults = coroutineScope {
            listOf(
                async { // [0] Treasure
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Treasure!A:G") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [1] Treasure_Values
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Treasure_Values!A:D") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [2] Factions
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Factions!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [3] Categories
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Categories!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [4] Subcategories
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Subcategories!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [5] Currencies
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Currencies!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async { // [6] Variants
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$SHEET_ID_TEST/values/Variants!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                }
            )
        }.awaitAll()


        val treasureEntities = deferredResults[0]
            .map { it.toTreasureEntityList() }
            .getOrElse { return Outcome.Error(it) }
        val valuesByTreasureId = deferredResults[1]
            .map { it.toTreasureValueDtoList() }
            .getOrElse { return Outcome.Error(it) }
            .groupBy { it.treasureId }
        val factionEntities = deferredResults[2]
            .map { it.toFactionEntities() }
            .getOrElse { return Outcome.Error(it) }
        val categoryEntities = deferredResults[3]
            .map { it.toCategoryEntities() }
            .getOrElse { return Outcome.Error(it) }
        val subcategoryEntities = deferredResults[4]
            .map { it.toSubcategoryEntities() }
            .getOrElse { return Outcome.Error(it) }
        val currenciesById = deferredResults[5]
            .map { it.toCurrencyDtoList() }
            .getOrElse { return Outcome.Error(it) }
            .associateBy { it.id }
        val variantEntities = deferredResults[6]
            .map { it.toVariantEntities() }
            .getOrElse { return Outcome.Error(it) }

        treasureDao.insertTreasure(treasureEntities)
        treasureDao.insertFactions(factionEntities)
        treasureDao.insertCategories(categoryEntities)
        treasureDao.insertSubcategories(subcategoryEntities)
        treasureDao.insertVariants(variantEntities)

        treasureEntities.forEach { treasureDto ->
            val treasureValues = valuesByTreasureId[treasureDto.id]
                ?.mapNotNull { it.toEntity(treasureDto.id, currenciesById) }
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

    override fun getEmissaries(): Flow<List<Emissary>> {
        return treasureDao.getAllEmissaries().map {
            it.map { emissaries -> emissaries.toDomain() }
        }
    }


    @Suppress("SpellCheckingInspection")
    companion object {
        const val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets"
        const val API_KEY = "AIzaSyBlAu-CRXh6hnsioN-wf7WB3BIYr3Dr24c"

        const val SHEET_ID_PROD = "1b3DQ7vPqF8EQq7lviwipRljqYzbyjXfRGJPn3ucc-ss"
        const val SHEET_ID_TEST = "1nu-iEo2SQIs4L-tYZno9YeGqg_YW1_j2kyFSTWyHpHs"
    }
}