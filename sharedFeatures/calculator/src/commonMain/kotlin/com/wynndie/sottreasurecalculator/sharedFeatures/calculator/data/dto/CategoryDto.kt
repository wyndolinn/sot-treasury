package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.SubcategoryDto.Companion.toDomain
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class CategoryDto(
    val id: Int,
    val name: String
) {
    companion object {
        fun CategoryDto.toDomain(
            subcategoriesTree: Map<Int, List<Treasure>>,
            subcategoriesDto: List<SubcategoryDto>
        ) = Category(
            id = id,
            name = name,
            subcategories = subcategoriesTree.mapNotNull { (subcategoryId, treasures) ->
                val subcategoryDto = subcategoriesDto.find { it.id == subcategoryId } ?: return@mapNotNull null
                subcategoryDto.toDomain(treasures)
            }
        )
    }
}
