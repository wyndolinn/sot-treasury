package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Faction(
    val id: Int,
    val name: String,
    val icon: String,
    val categories: List<Category>
)