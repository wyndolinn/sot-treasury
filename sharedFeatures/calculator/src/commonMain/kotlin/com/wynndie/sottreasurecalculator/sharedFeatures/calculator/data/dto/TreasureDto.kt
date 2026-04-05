package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureValue

data class TreasureDto(
    val id: Int,
    val sellableTo: List<Int>,
    val factions: List<Int>,
    val category: Int,
    val subcategory: Int?,
    val name: String
) {
    fun toDomain(values: List<TreasureValue>) = Treasure(
        id = id,
        name = name,
        sellableTo = sellableTo,
        values = values
    )

    companion object {
        fun from(response: List<String>): TreasureDto {
            return TreasureDto(
                id = response[0].toInt(),
                sellableTo = response[1].split(",").map { it.toInt() },
                factions = response[2].split(",").map { it.toInt() },
                category = response[3].toInt(),
                subcategory = response[4].toIntOrNull(),
                name = response[5]
            )
        }
    }
}
