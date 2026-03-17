package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models

data class Category(
    val id: Int,
    val title: String,
    val subcategories: List<Subcategory>
)
