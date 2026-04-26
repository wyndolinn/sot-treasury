package com.wynndie.sottreasury.sharedFeatures.calculator.domain.models

data class Faction(
    val id: Int,
    val name: String,
    val icon: String,
    val categories: Map<Int, Category>
)