package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class TreasurePrice(
    val currencies: Currencies,
    val minPrice: Int,
    val maxPrice: Int
)
