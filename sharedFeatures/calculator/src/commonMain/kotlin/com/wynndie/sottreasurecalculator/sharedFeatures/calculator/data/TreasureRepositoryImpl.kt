package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.CategoryDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.CurrencyDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.FactionDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.SubcategoryDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.TreasureDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.ValueDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository

class TreasureRepositoryImpl : TreasureRepository {
    override suspend fun loadTreasure(): Outcome<List<Faction>, DataError.Remote> {
        val dummyTreasure = buildDummyTreasure()
        return Outcome.Success(dummyTreasure)
    }


    private fun buildDummyTreasure(): List<Faction> {
        val factionsDto = listOf(
            FactionDto(0, "Gold Hoarders", ""),
            FactionDto(1, "Order of Souls", ""),
            FactionDto(2, "Merchant Alliance", ""),
            FactionDto(3, "Reaper's Bones", "")
        )

        val categoriesDto = listOf(
            CategoryDto(0, "Shared Treasure", ""),
            CategoryDto(1, "Common Treasure", "")
        )

        val subcategoriesDto = listOf(
            SubcategoryDto(0, "Common", ""),
            SubcategoryDto(1, "Ashen", ""),
            SubcategoryDto(2, "Coral", "")
        )

        val currenciesDto = listOf(
            CurrencyDto(0, "Gold", ""),
            CurrencyDto(1, "Doubloons", ""),
            CurrencyDto(2, "EmissaryValue", "")
        )

        val treasureDto = listOf(
            TreasureDto(
                id = 0,
                name = "Ruby gem",
                factions = listOf(0, 1, 2, 3),
                category = 0,
                subcategory = 0,
                values = listOf(
                    ValueDto(1, 30, 30),
                    ValueDto(2, 5000, 5000)
                )
            ),
            TreasureDto(
                id = 1,
                name = "Emerald gem",
                factions = listOf(0, 1, 3),
                category = 0,
                subcategory = 0,
                values = listOf(
                    ValueDto(1, 20, 20),
                    ValueDto(2, 5000, 5000)
                )
            ),
            TreasureDto(
                id = 2,
                name = "Sapphire gem",
                factions = listOf(1, 2, 3),
                category = 0,
                subcategory = 0,
                values = listOf(
                    ValueDto(1, 10, 10),
                    ValueDto(2, 5000, 5000)
                )
            ),
            TreasureDto(
                id = 3,
                name = "Captain's chest",
                factions = listOf(0, 3),
                category = 1,
                subcategory = 0,
                values = listOf(
                    ValueDto(0, 1000, 1200),
                    ValueDto(2, 5000, 5000)
                )
            ),
            TreasureDto(
                id = 4,
                name = "Villainous skull",
                factions = listOf(1, 3),
                category = 1,
                subcategory = 0,
                values = listOf(
                    ValueDto(0, 1100, 1300),
                    ValueDto(2, 5000, 5000)
                )
            ),
            TreasureDto(
                id = 5,
                name = "Crate of Silks",
                factions = listOf(2, 3),
                category = 1,
                subcategory = 0,
                values = listOf(
                    ValueDto(0, 1200, 1400),
                    ValueDto(2, 5000, 5000)
                )
            )
        )

        val tree = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableList<Treasure>>>>()
        treasureDto.forEach { dto ->
            val treasure = dto.toDomain(currenciesDto)
            dto.factions.forEach { factionId ->
                tree
                    .getOrPut(factionId) { mutableMapOf() }
                    .getOrPut(dto.category) { mutableMapOf() }
                    .getOrPut(dto.subcategory) { mutableListOf() }
                    .add(treasure)
            }
        }

        return factionsDto.mapNotNull { factionDto ->
            val categories = tree[factionDto.id] ?: return@mapNotNull null
            factionDto.toDomain(categories, categoriesDto, subcategoriesDto)
        }
    }
}