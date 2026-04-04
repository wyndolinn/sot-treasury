package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

import androidx.compose.ui.graphics.Color

data class Emissary(
    val id: Int,
    val name: String,
    val icon: String,
    val color: Color = Color.Blue,
    val grades: List<Float>
)
