package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto.CategoryDto.Companion.toDomain
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class FactionDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    companion object {
        fun FactionDto.toDomain(
            categoriesTree: Map<Int, Map<Int, List<Treasure>>>,
            categoriesDto: List<CategoryDto>,
            subcategoriesDto: List<SubcategoryDto>
        ) = Faction(
            id = id,
            name = name,
            icon = icon,
            categories = categoriesTree.mapNotNull { (categoryId, subcategoriesTree) ->
                val categoryDto = categoriesDto.find { it.id == categoryId } ?: return@mapNotNull null
                categoryDto.toDomain(subcategoriesTree, subcategoriesDto)
            }
        )
    }
}
