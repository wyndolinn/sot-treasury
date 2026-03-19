package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction

data class TreasureState(
    val loadingState: LoadingState = LoadingState.Finished,
    val treasure: List<Faction> = emptyList(),
    val selectedFactionPage: Int = 0,
    val selectedSubcategories: Map<Int, Map<Int, Int>> = mapOf(),
    val treasureAmounts: Map<Int, Int> = mapOf()
)