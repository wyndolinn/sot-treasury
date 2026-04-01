package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val subcategories: List<Subcategory>
)
