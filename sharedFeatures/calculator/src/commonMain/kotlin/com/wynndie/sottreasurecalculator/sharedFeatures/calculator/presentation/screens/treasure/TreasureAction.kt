package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.models.TreasureKey

sealed interface TreasureAction {
    data class OnClickSubcategory(
        val categoryIndex: Int,
        val subcategoryIndex: Int
    ) : TreasureAction

    data class OnChangeTreasureCount(
        val treasureKey: TreasureKey,
        val amount: Int
    ) : TreasureAction
}