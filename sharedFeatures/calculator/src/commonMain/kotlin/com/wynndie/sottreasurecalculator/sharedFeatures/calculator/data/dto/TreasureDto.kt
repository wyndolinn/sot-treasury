package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Currencies
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasurePrice

data class TreasureDto(
    val id: Int,
    val name: String,
    val factions: List<Int>,
    val category: Int,
    val subcategory: Int,
    val currency: Int,
    val minPrice: Int,
    val maxPrice: Int
) {
    companion object {
        fun TreasureDto.toDomain() = Treasure(
            id = id,
            name = name,
            factions = factions,
            currencies = listOf(TreasurePrice(Currencies.GOLD, minPrice, maxPrice))
        )
    }
}
