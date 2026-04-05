package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary

data class EmissaryDto(
    val id: Int,
    val icon: String,
    val color: String,
    val grades: List<Float>,
    val name: String
) {
    fun toDomain(): Emissary {
        return Emissary(
            id = id,
            name = name,
            icon = icon,
            grades = grades
        )
    }

    companion object {
        fun from(response: List<String>): EmissaryDto {
            return EmissaryDto(
                id = response[0].toInt(),
                icon = response.getOrNull(1) ?: "",
                color = response.getOrNull(2) ?: "",
                grades = response[3].split(", ").map { it.toFloat() },
                name = response[4]
            )
        }
    }
}
