package com.wynndie.sottreasury.sharedFeatures.calculator.domain.models

data class Subcategory(
    val id: Int,
    val name: String,
    val icon: String,
    val treasure: List<Treasure>
)
