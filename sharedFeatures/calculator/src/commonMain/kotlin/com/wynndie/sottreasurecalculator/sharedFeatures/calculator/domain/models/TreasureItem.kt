package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class TreasureItem(
    val id: Int,
    val title: String,
    val factions: List<Faction>,
    val currency: Currency,
    val minPrice: Int,
    val maxPrice: Int,
    val minAmount: Int,
    val maxAmount: Int
)
