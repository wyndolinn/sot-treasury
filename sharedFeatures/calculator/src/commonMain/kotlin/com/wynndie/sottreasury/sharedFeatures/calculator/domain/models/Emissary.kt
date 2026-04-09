package com.wynndie.sottreasury.sharedFeatures.calculator.domain.models

data class Emissary(
    val id: Int,
    val name: String,
    val icon: String,
    val color: String,
    val grades: List<Float>
)
