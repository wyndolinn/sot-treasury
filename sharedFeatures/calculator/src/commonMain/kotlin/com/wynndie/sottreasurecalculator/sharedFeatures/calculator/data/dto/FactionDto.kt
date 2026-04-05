package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import kotlinx.serialization.Serializable

data class FactionDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        categoriesTree: Map<Int, Map<Int, List<Treasure>>>,
        categoriesDto: List<CategoryDto>,
        subcategoriesDto: List<SubcategoryDto>
    ): Faction {
        return Faction(
            id = id,
            name = name,
            icon = icon,
            categories = categoriesTree.mapNotNull { (categoryId, subcategoriesTree) ->
                categoriesDto
                    .find { it.id == categoryId }
                    ?.toDomain(subcategoriesTree, subcategoriesDto)
                    ?: return@mapNotNull null
            }
        )
    }

    companion object {
        fun from(response: List<String>): FactionDto {
            return FactionDto(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
