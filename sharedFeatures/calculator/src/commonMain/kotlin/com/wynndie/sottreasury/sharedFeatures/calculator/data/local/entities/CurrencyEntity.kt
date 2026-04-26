package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Entity
data class CurrencyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String,
    val sortOrder: Int
) {
    fun toDomain(): TreasureValue {
        return TreasureValue(
            currencyId = id,
            name = name,
            icon = icon,
            minPrice = 0,
            maxPrice = 0
        )
    }

    companion object {
        fun from(response: List<String>): CurrencyEntity {
            println("PRINTLINE: response $response")
            return CurrencyEntity(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: "",
                sortOrder = response.getOrNull(3)?.toIntOrNull() ?: 0
            )
        }
    }
}