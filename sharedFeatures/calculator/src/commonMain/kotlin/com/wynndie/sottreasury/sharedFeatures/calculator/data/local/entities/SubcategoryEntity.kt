package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure

@Entity
data class SubcategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        variantsTree: Map<Int, Map<Int, Treasure>>,
        variants: Map<Int, VariantEntity>
    ): Subcategory {
        return Subcategory(
            id = id,
            name = name,
            icon = icon,
            variants = variantsTree.map { (variantId, treasure) ->
                variants.toMutableMap()
                    .getOrPut(variantId) { VariantEntity(-1, "", "") }
                    .toDomain(treasure)
            }.associateBy { it.id }
        )
    }

    companion object {
        fun from(response: List<String>): SubcategoryEntity {
            return SubcategoryEntity(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
