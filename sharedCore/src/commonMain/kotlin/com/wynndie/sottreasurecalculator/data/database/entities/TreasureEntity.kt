package com.wynndie.sottreasurecalculator.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TreasureEntity(
    @PrimaryKey val id: Int,
)
