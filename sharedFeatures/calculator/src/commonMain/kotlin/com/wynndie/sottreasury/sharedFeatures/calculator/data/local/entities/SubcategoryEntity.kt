package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant
import kotlin.collections.component1
import kotlin.collections.component2

@Entity
data class SubcategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(
        variantsTree: Map<Int, List<Treasure>>,
        variants: List<VariantEntity>
    ): Subcategory{
        return Subcategory(
            id = id,
            name = name,
            icon = icon,
            variants = variantsTree.map { (variantId, treasure) ->
                val variant = variants.find { it.id == variantId } ?: VariantEntity(-1, "", "")
                variant.toDomain(treasure)
            }
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
