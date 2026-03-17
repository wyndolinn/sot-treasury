package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.models.TreasureKey

data class TreasureState(
    val treasure: List<Category> = emptyList(),
    val selectedSubcategories: Map<Int, Int> = mapOf(),
    val treasureAmounts: Map<TreasureKey, Int> = mapOf()
)