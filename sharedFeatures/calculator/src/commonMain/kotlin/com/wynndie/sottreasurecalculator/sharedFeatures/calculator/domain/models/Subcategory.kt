package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Subcategory(
    val id: Int,
    val title: String,
    val items: List<Treasure>
)
