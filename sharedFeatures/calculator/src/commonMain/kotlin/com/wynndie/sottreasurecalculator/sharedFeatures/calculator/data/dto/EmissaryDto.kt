package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary

data class EmissaryDto(
    val id: Int,
    val name: String,
    val icon: String,
    val color: String,
    val grades: List<Float>
) {
    fun toDomain(): Emissary {
        return Emissary(
            id = id,
            name = name,
            icon = icon,
            grades = grades
        )
    }
}
