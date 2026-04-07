package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

@Entity
data class SubcategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(treasures: List<Treasure>) = Subcategory(
        id = id,
        name = name,
        icon = icon,
        treasure = treasures
    )

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
