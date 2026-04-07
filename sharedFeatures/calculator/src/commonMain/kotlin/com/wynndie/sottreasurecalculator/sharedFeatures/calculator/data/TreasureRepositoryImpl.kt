package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data

import com.wynndie.sottreasurecalculator.sharedCore.data.remote.safeCall
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.map
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.ResponseDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.dao.TreasureDao
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository
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
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Treasure!A:F") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Treasure_Values!A:F") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Factions!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Categories!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Subcategories!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                },
                async {
                    safeCall<ResponseDto> {
                        httpClient.get("$BASE_URL/$ID/values/Currencies!A:C") {
                            parameter("key", API_KEY)
                        }
                    }
                }
            )
        }.awaitAll()


        val treasureEntities = deferredResults[0]
            .map { it.toTreasureEntityList() }
            .getOrElse { return Outcome.Error(it) }
        val factionEntities = deferredResults[2]
            .map { it.toFactionEntities() }
            .getOrElse { return Outcome.Error(it) }
        val categoryEntities = deferredResults[3]
            .map { it.toCategoryEntities() }
            .getOrElse { return Outcome.Error(it) }
        val subcategoryEntities = deferredResults[4]
            .map { it.toSubcategoryEntities() }
            .getOrElse { return Outcome.Error(it) }

        treasureDao.insertTreasure(treasureEntities)
        treasureDao.insertFactions(factionEntities)
        treasureDao.insertCategories(categoryEntities)
        treasureDao.insertSubcategories(subcategoryEntities)


        val valuesByTreasureId = deferredResults[1]
            .map { it.toTreasureValueDtoList() }
            .getOrElse { return Outcome.Error(it) }
            .groupBy { it.treasureId }
        val currenciesById = deferredResults[5]
            .map { it.toCurrencyDtoList() }
            .getOrElse { return Outcome.Error(it) }
            .associateBy { it.id }

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
            httpClient.get("$BASE_URL/$ID/values/Emissaries!A:E") {
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
            treasureDao.getAllSubcategory()
        ) { allTreasure, allFactions, allCategories, allSubcategories ->

            val tree = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableList<Treasure>>>>()
            allTreasure.forEach { treasureWithValues ->
                val treasureEntity = treasureWithValues.treasure
                val treasure = treasureWithValues.toDomain()
                treasureEntity.factions.split(",").forEach { factionId ->
                    tree
                        .getOrPut(factionId.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.category.toInt()) { mutableMapOf() }
                        .getOrPut(treasureEntity.subcategory.toInt()) { mutableListOf() }
                        .add(treasure)
                }
            }

            allFactions.mapNotNull { faction ->
                val categories = tree[faction.id] ?: return@mapNotNull null
                faction.toDomain(categories, allCategories, allSubcategories)
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
        const val ID = "1b3DQ7vPqF8EQq7lviwipRljqYzbyjXfRGJPn3ucc-ss"
        const val API_KEY = "AIzaSyCeStCTxsllXdoepyUSGKN88sWPVldcm58"
    }
}