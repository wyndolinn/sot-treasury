package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure

@Entity
data class FactionEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        categoriesTree: Map<Int, Map<Int, Map<Int, List<Treasure>>>>,
        categories: List<CategoryEntity>,
        subcategories: List<SubcategoryEntity>,
        variants: List<VariantEntity>
    ): Faction {
        return Faction(
            id = id,
            name = name,
            icon = icon,
            categories = categoriesTree.mapNotNull { (categoryId, subcategoriesTree) ->
                categories
                    .find { it.id == categoryId }
                    ?.toDomain(subcategoriesTree, subcategories, variants)
                    ?: return@mapNotNull null
            }
        )
    }

    companion object {
        fun from(response: List<String>): FactionEntity {
            return FactionEntity(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
