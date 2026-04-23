package com.wynndie.sottreasury.sharedFeatures.calculator.domain.models

data class Variant(
    val id: Int,
    val name: String,
    val icon: String,
    val treasure: List<Treasure>
)
