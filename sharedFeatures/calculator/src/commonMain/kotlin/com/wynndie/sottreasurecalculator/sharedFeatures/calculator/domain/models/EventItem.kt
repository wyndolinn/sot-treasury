package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class EventItem(
    val id: Int,
    val title: String,
    val treasure: List<TreasureItem>
)
