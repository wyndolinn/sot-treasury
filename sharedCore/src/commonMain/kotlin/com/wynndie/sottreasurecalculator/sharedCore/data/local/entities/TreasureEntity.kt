package com.wynndie.sottreasurecalculator.sharedCore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TreasureEntity(
    @PrimaryKey val id: Int,
)
