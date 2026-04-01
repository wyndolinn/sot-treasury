package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class TreasureDto(
    val id: Int,
    val name: String,
    val factions: List<Int>,
    val category: Int,
    val subcategory: Int,
    val values: List<ValueDto>
) {
    fun toDomain(currencies: List<CurrencyDto>) = Treasure(
        id = id,
        name = name,
        factions = factions,
        values = values.map { it.toDomain(currencies) }
    )
}
