package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TreasureEntity(
    @PrimaryKey val id: Int,
    val sellableTo: String,
    val factions: String,
    val category: String,
    val subcategory: String,
    val name: String
) {

    companion object {
        fun from(response: List<String>): TreasureEntity {
            return TreasureEntity(
                id = response[0].toInt(),
                sellableTo = response[1],
                factions = response[2],
                category = response[3],
                subcategory = response[4].ifBlank { "0" },
                name = response[5]
            )
        }
    }
}