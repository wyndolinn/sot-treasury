package com.wynndie.sottreasury.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureValueEntity

data class TreasureValueDto(
    val treasureId: Int,
    val currencyId: Int?,
    val minValue: Int?,
    val maxValue: Int?
) {
    fun toEntity(
        treasureId: Int,
        currencies: Map<Int, CurrencyDto>
    ): TreasureValueEntity? {
        val currency = currencies[currencyId] ?: return null
        return TreasureValueEntity(
            treasureId = treasureId,
            currencyId = currencyId ?: return null,
            name = currency.name,
            icon = currency.icon,
            minPrice = minValue ?: maxValue ?: return null,
            maxPrice = maxValue ?: minValue ?: return null
        )
    }

    companion object {
        fun from(response: List<String>): TreasureValueDto {
            return TreasureValueDto(
                treasureId = response[0].toInt(),
                currencyId = response.getOrNull(1)?.toIntOrNull(),
                minValue = response.getOrNull(2)?.toIntOrNull(),
                maxValue = response.getOrNull(3)?.toIntOrNull()
            )
        }
    }
}
