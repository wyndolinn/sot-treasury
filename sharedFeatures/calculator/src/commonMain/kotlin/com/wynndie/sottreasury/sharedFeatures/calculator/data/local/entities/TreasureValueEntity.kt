package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Entity(
    primaryKeys = ["treasureId", "currencyId"]
)
data class TreasureValueEntity(
    val treasureId: Int,
    val currencyId: Int,
    val name: String,
    val icon: String,
    val minPrice: Int,
    val maxPrice: Int
) {
    fun toDomain(): TreasureValue {
        return TreasureValue(
            currencyId = currencyId,
            name = name,
            icon = icon,
            minPrice = minPrice,
            maxPrice = maxPrice
        )
    }
}
