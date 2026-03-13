package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureItem

data class TreasureState(
    val treasure: List<TreasureItem> = emptyList()
)