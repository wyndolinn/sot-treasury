package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant

@Entity
data class VariantEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String
) {
    fun toDomain(treasure: List<Treasure>): Variant {
        return Variant(
            id = id,
            name = name,
            icon = icon,
            treasure = treasure
        )
    }

    companion object {
        fun from(response: List<String>): VariantEntity {
            return VariantEntity(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
