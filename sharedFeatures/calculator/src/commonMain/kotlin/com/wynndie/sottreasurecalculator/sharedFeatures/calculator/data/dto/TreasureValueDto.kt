package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureValue

data class TreasureValueDto(
    val treasureId: Int,
    val currencyId: Int?,
    val minValue: Int?,
    val maxValue: Int?
) {
    fun toDomain(currencies: Map<Int, CurrencyDto>): TreasureValue? {
        if (currencyId == null) return null
        val currency = currencies[currencyId]
        return TreasureValue(
            currencyId = currencyId,
            name = currency?.name ?: "",
            icon = currency?.icon ?: "",
            minPrice = minValue,
            maxPrice = maxValue
        )
    }

    companion object {
        fun from(response: List<String>): TreasureValueDto {
            return TreasureValueDto(
                treasureId = response[0].toInt(),
                currencyId = response[1].toIntOrNull(),
                minValue = response[2].toIntOrNull(),
                maxValue = response[3].toIntOrNull()
            )
        }
    }
}
