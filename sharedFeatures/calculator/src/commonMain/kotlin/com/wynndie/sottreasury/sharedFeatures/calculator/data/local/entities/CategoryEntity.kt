package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant

@Entity
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        subcategoriesTree: Map<Int, Map<Int, List<Treasure>>>,
        subcategories: List<SubcategoryEntity>,
        variants: List<VariantEntity>
    ): Category {
        return Category(
            id = id,
            name = name,
            icon = icon,
            subcategories = subcategoriesTree.mapNotNull { (subcategoryId, variantsTree) ->
                subcategories
                    .find { it.id == subcategoryId }
                    ?.toDomain(variantsTree, variants)
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
