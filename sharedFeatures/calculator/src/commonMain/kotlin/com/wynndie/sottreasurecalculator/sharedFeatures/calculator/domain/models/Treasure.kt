package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Treasure(
    val id: Int,
    val name: String,
    val factions: List<Int>,
    val values: List<TreasureValue>
)
