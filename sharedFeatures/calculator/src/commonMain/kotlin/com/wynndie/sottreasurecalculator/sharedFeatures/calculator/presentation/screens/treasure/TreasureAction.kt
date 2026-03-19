package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

sealed interface TreasureAction {
    data class OnClickSubcategory(
        val factionId: Int,
        val categoryId: Int,
        val subcategoryId: Int
    ) : TreasureAction

    data class OnChangeTreasureCount(
        val treasureId: Int,
        val amount: Int
    ) : TreasureAction

    data class OnChangeFactionPage(val id: Int) : TreasureAction
}