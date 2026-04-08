package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class TreasureValue(
    val currencyId: Int,
    val name: String,
    val icon: String,
    val minPrice: Int?,
    val maxPrice: Int?
)
