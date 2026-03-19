package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class SubcategoryDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    companion object {
        fun SubcategoryDto.toDomain(treasures: List<Treasure>) = Subcategory(
            id = id,
            name = name,
            icon = icon,
            items = treasures
        )
    }
}
