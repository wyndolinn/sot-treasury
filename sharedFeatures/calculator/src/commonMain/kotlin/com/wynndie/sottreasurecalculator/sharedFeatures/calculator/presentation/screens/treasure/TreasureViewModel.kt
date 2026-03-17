package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.lifecycle.ViewModel
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Currencies
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Factions
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Subcategory
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasurePrice
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.models.TreasureKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TreasureViewModel : ViewModel() {

    private val _state = MutableStateFlow(TreasureState())
    val state = _state.asStateFlow()


    init {
        loadDummyData()
    }


    fun onAction(action: TreasureAction) {
        when (action) {
            is TreasureAction.OnClickSubcategory ->
                onClickSubcategory(action.categoryIndex, action.subcategoryIndex)

            is TreasureAction.OnChangeTreasureCount ->
                onChangeTreasureCount(action.treasureKey, action.amount)
        }
    }


    private fun onClickSubcategory(categoryIndex: Int, subcategoryIndex: Int) {
        _state.update { state ->
            state.copy(
                selectedSubcategories = state.selectedSubcategories.toMutableMap().apply {
                    put(categoryIndex, subcategoryIndex)
                }
            )
        }
    }

    private fun onChangeTreasureCount(treasureKey: TreasureKey, amount: Int) {
        _state.update { state ->
            state.copy(
                treasureAmounts = state.treasureAmounts.toMutableMap().apply {
                    put(treasureKey, amount)
                }
            )
        }
    }


    private fun loadDummyData() {
        _state.update {
            it.copy(
                treasure = listOf(
                    Category(
                        id = 0,
                        title = "Сундуки",
                        subcategories = listOf(
                            Subcategory(
                                id = 0,
                                title = "Обыкновенные",
                                items = listOf(
                                    Treasure(
                                        id = 0,
                                        title = "Королевский сундук",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    ),
                                    Treasure(
                                        id = 1,
                                        title = "Сундук барона",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            ),
                            Subcategory(
                                id = 1,
                                title = "Пепельные",
                                items = listOf(
                                    Treasure(
                                        id = 2,
                                        title = "Пепельный королевский сундук",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            ),
                            Subcategory(
                                id = 2,
                                title = "Коралловые",
                                items = listOf(
                                    Treasure(
                                        id = 3,
                                        title = "Кораловый королевский сундук",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            )
                        )
                    ),
                    Category(
                        id = 1,
                        title = "Артефакты",
                        subcategories = listOf(
                            Subcategory(
                                id = 3,
                                title = "Обыкновенные",
                                items = listOf(
                                    Treasure(
                                        id = 4,
                                        title = "Королевская корона",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            ),
                            Subcategory(
                                id = 4,
                                title = "Пепельные",
                                items = listOf(
                                    Treasure(
                                        id = 5,
                                        title = "Пепельная королевская корона",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            ),
                            Subcategory(
                                id = 5,
                                title = "Коралловые",
                                items = listOf(
                                    Treasure(
                                        id = 6,
                                        title = "Кораловое яйцо фаберже",
                                        factions = listOf(Factions.GOLD_HOARDERS),
                                        currencies = listOf(TreasurePrice(Currencies.GOLD, 10, 20)),
                                    )
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}