package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary

@Entity
data class EmissaryEntity(
    @PrimaryKey val id: Int,
    val icon: String,
    val color: String,
    val grades: String,
    val name: String
) {
    fun toDomain(): Emissary {
        return Emissary(
            id = id,
            name = name,
            icon = icon,
            color = color,
            grades = grades.split(",").map { it.toFloat() }
        )
    }

    companion object {
        fun from(response: List<String>): EmissaryEntity {
            return EmissaryEntity(
                id = response[0].toInt(),
                icon = response.getOrNull(1) ?: "",
                color = response.getOrNull(2) ?: "",
                grades = response[3],
                name = response[4]
            )
        }
    }
}