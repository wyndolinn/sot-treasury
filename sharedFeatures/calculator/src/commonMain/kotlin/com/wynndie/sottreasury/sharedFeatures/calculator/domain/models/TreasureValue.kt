package com.wynndie.sottreasury.sharedFeatures.calculator.domain.models

data class TreasureValue(
    val currencyId: Int,
    val name: String,
    val icon: String,
    val minPrice: Int?,
    val maxPrice: Int?
)
