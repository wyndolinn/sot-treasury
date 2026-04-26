package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TreasureEntity(
    @PrimaryKey val id: Int,
    val sellableTo: String,
    val factions: String,
    val category: String,
    val subcategory: String,
    val variant: String,
    val name: String
) {
    companion object {
        fun from(response: List<String>): TreasureEntity {
            return TreasureEntity(
                id = response[0].toInt(),
                sellableTo = response[1],
                factions = response[2],
                category = response[3],
                subcategory = response.getOrNull(4)?.ifBlank { "0" } ?: "0",
                variant = response.getOrNull(6)?.ifBlank { "-1" } ?: "-1",
                name = response[5]
            )
        }
    }
}