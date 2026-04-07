package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

@Entity
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        subcategoriesTree: Map<Int, List<Treasure>>,
        subcategories: List<SubcategoryEntity>
    ): Category {
        return Category(
            id = id,
            name = name,
            icon = icon,
            subcategories = subcategoriesTree.mapNotNull { (subcategoryId, treasures) ->
                subcategories
                    .find { it.id == subcategoryId }
                    ?.toDomain(treasures)
                    ?: return@mapNotNull null
            }
        )
    }

    companion object {
        fun from(response: List<String>): CategoryEntity {
            return CategoryEntity(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
