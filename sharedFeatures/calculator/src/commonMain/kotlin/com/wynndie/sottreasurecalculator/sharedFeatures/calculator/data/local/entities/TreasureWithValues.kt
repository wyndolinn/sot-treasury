package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

data class TreasureWithValues(
    @Embedded
    val treasure: TreasureEntity,

    @Relation(parentColumn = "id", entityColumn = "treasureId")
    val values: List<TreasureValueEntity>
) {
    fun toDomain(): Treasure {
        return Treasure(
            id = treasure.id,
            name = treasure.name,
            sellableTo = treasure.sellableTo.split(",").map { it.toInt() },
            values = values.map { it.toDomain() }
        )
    }
}
