package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data

import com.wynndie.sottreasurecalculator.sharedCore.data.remote.safeCall
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.map
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.ResponseDto
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

class TreasureRepositoryImpl(
    private val httpClient: HttpClient
) : TreasureRepository {
    override suspend fun loadTreasure(): Outcome<List<Faction>, DataError.Remote> {

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


        val treasureDtoList = deferredResults[0]
            .map { it.toTreasureDtoList() }
            .getOrElse { return Outcome.Error(it) }
        val treasureValueDtoList = deferredResults[1]
            .map { it.toTreasureValueDtoList() }
            .getOrElse { return Outcome.Error(it) }
        val factionDtoList = deferredResults[2]
            .map { it.toFactionDtoList() }
            .getOrElse { return Outcome.Error(it) }
        val categoryDtoList = deferredResults[3]
            .map { it.toCategoryDtoList() }
            .getOrElse { return Outcome.Error(it) }
        val subcategoryDtoList = deferredResults[4]
            .map { it.toSubcategoryDtoList() }
            .getOrElse { return Outcome.Error(it) }
        val currencyDtoList = deferredResults[5]
            .map { it.toCurrencyDtoList() }
            .getOrElse { return Outcome.Error(it) }


        val valuesByTreasureId = treasureValueDtoList.groupBy { it.treasureId }
        val currenciesById = currencyDtoList.associateBy { it.id }

        val tree = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableList<Treasure>>>>()
        treasureDtoList.forEach { treasureDto ->
            val treasureValues = valuesByTreasureId[treasureDto.id]
                ?.mapNotNull { it.toDomain(currenciesById) }
                ?: emptyList()
            val treasure = treasureDto.toDomain(treasureValues)
            treasureDto.factions.forEach { factionId ->
                tree
                    .getOrPut(factionId) { mutableMapOf() }
                    .getOrPut(treasureDto.category) { mutableMapOf() }
                    .getOrPut(treasureDto.subcategory ?: 0) { mutableListOf() }
                    .add(treasure)
            }
        }

        val factions = factionDtoList.mapNotNull { factionDto ->
            val categories = tree[factionDto.id] ?: return@mapNotNull null
            factionDto.toDomain(categories, categoryDtoList, subcategoryDtoList)
        }

        return Outcome.Success(factions)
    }


    override suspend fun loadEmissaries(): Outcome<List<Emissary>, DataError.Remote> {
        val emissaryDtoList = safeCall<ResponseDto> {
            httpClient.get("$BASE_URL/$ID/values/Emissaries!A:E") {
                parameter("key", API_KEY)
            }
        }.map { it.toEmissaryDtoList() }.getOrElse { return Outcome.Error(it) }

        val emissaries = emissaryDtoList.map { it.toDomain() }
        return Outcome.Success(emissaries)
    }

    @Suppress("SpellCheckingInspection")
    companion object {
        const val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets"
    }
}