package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Treasure(
    val id: Int,
    val title: String,
    val factions: List<Factions>,
    val currencies: List<TreasurePrice>
)
