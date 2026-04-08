package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

sealed interface TreasureAction {

    data object ReloadData : TreasureAction
    data class SelectFactionPage(val id: Int) : TreasureAction
    data class SelectEmissaryGrade(val level: Int) : TreasureAction
    data class ToggleEmissaryPicker(val open: Boolean) : TreasureAction
    data class SelectEmissary(val id: Int) : TreasureAction

    data class SelectSubcategory(
        val factionId: Int,
        val categoryId: Int,
        val subcategoryId: Int
    ) : TreasureAction

    data class ChangeTreasureAmount(
        val treasureId: Int,
        val amount: Int
    ) : TreasureAction
}