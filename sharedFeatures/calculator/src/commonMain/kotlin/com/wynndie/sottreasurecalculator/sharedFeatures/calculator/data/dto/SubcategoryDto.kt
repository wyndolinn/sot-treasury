package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class SubcategoryDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(treasures: List<Treasure>) = Subcategory(
        id = id,
        name = name,
        icon = icon,
        treasure = treasures
    )

    companion object {
        fun from(response: List<String>): SubcategoryDto {
            return SubcategoryDto(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
