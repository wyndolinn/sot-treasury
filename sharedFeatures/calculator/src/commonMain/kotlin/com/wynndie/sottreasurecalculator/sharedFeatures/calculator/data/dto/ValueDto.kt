package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureValue

data class ValueDto(
    val currencyId: Int,
    val minValue: Int,
    val maxValue: Int
) {
    fun toDomain(currencies: List<CurrencyDto>): TreasureValue {
        val currency = currencies.find { it.id == currencyId }
        return TreasureValue(
            id = currencyId,
            name = currency?.name ?: "",
            icon = currency?.icon ?: "",
            minPrice = minValue,
            maxPrice = maxValue
        )
    }
}
