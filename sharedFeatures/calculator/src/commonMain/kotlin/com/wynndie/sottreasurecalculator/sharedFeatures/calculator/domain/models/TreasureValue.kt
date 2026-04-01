package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class TreasureValue(
    val id: Int,
    val name: String,
    val icon: String,
    val minPrice: Int,
    val maxPrice: Int
)
