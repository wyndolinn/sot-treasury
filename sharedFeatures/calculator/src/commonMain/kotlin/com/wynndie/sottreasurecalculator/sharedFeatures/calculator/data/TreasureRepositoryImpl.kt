package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.CategoryDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.CurrencyDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.FactionDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.FactionDto.Companion.toDomain
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.SubcategoryDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.TreasureDto
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.TreasureDto.Companion.toDomain
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository
import kotlin.random.Random

class TreasureRepositoryImpl : TreasureRepository {
    override suspend fun loadTreasure(): Outcome<List<Faction>, DataError.Remote> {
        return Outcome.Success(buildDummyFactions())
    }


    private fun buildDummyFactions(): List<Faction> {
        val rng = Random(seed = 67)

        val factionsDto = (0..5).map {
            FactionDto(it, "Faction $it", "")
        }
        val categoriesDto = (0..20).map {
            CategoryDto(it, "Category $it")
        }
        val subcategoriesDto = (0..10).map {
            SubcategoryDto(it, "Subcategory $it", "")
        }
        val currenciesDto = (0..2).map {
            CurrencyDto(it, "Currency$it", "")
        }
        val treasureDto = (0..300).map { id ->
            TreasureDto(
                id = id,
                name = "Treasure $id",
                factions = (0..rng.nextInt(0, 2)).map { factionsDto.random(rng).id },
                category = categoriesDto.random(rng).id,
                subcategory = subcategoriesDto.random(rng).id,
                currency = currenciesDto.random(rng).id,
                minPrice = rng.nextInt(200, 700),
                maxPrice = rng.nextInt(900, 1500)
            )
        }

        val tree = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableList<Treasure>>>>()

        treasureDto.forEach { dto ->
            val treasure = dto.toDomain()
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