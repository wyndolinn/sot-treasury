package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure

import androidx.compose.runtime.Stable
import com.wynndie.sottreasury.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Stable
data class TreasureState(
    val loadingState: LoadingState = LoadingState.Finished,
    val factions: Map<Int, Faction> = mapOf(),
    val currencies: List<TreasureValue> = emptyList(),
    val selectedFactionPage: Int = 0,
    val selectedSubcategories: Map<Int, Map<Int, Int>> = mapOf(),
    val treasureAmounts: Map<Int, Int> = mapOf(),
    val valuePerEmissary: Map<Int, Map<Int, Pair<Int, Int>>> = mapOf(),
    val totalValues: Map<Int, Pair<Int, Int>> = mapOf(),
    val emissaries: List<Emissary> = emptyList(),
    val isEmissaryPickerOpen: Boolean = false,
    val selectedEmissary: Int = 0,
    val emissaryGrade: Int = 0,
    val emissaryMultiplier: Float = 1f
)