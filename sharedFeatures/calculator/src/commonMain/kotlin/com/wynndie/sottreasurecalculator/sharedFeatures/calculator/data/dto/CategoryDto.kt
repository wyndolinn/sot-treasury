package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class CategoryDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        subcategoriesTree: Map<Int, List<Treasure>>,
        subcategoriesDto: List<SubcategoryDto>
    ): Category {
        return Category(
            id = id,
            name = name,
            icon = icon,
            subcategories = subcategoriesTree.mapNotNull { (subcategoryId, treasures) ->
                subcategoriesDto
                    .find { it.id == subcategoryId }
                    ?.toDomain(treasures)
                    ?: return@mapNotNull null
            }
        )
    }

    companion object {
        fun from(response: List<String>): CategoryDto {
            return CategoryDto(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
