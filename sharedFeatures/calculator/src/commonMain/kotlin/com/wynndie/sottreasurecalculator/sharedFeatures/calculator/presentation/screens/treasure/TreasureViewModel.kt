package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.onSuccess
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreasureViewModel(
    private val treasureRepository: TreasureRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TreasureState())
    val state = _state.asStateFlow()


    init {
        loadDummyData()
    }


    fun onAction(action: TreasureAction) {
        when (action) {
            is TreasureAction.OnClickSubcategory ->
                onClickSubcategory(action.factionId, action.categoryId, action.subcategoryId)

            is TreasureAction.OnChangeTreasureCount ->
                onChangeTreasureCount(action.treasureId, action.amount)

            is TreasureAction.OnChangeFactionPage ->
                onChangeFactionPage(action.id)
        }
    }


    private fun onClickSubcategory(factionId: Int, categoryId: Int, subcategoryId: Int) {
        _state.update { state ->
            state.copy(
                selectedSubcategories = state.selectedSubcategories.toMutableMap().apply {
                    val categories = getOrElse(factionId) { mapOf() }.toMutableMap()
                    categories[categoryId] = subcategoryId
                    put(factionId, categories)
                }
            )
        }
    }

    private fun onChangeTreasureCount(treasureId: Int, amount: Int) {
        _state.update { state ->
            state.copy(
                treasureAmounts = state.treasureAmounts.toMutableMap().apply {
                    put(treasureId, amount)
                }
            )
        }
    }

    private fun onChangeFactionPage(id: Int) {
        _state.update {
            it.copy(selectedFactionPage = id)
        }
    }


    private fun loadDummyData() {
        viewModelScope.launch {
            _state.update { it.copy(loadingState = LoadingState.Loading) }

            treasureRepository.loadTreasure()
                .onSuccess { treasure ->
                    _state.update { it.copy(treasure = treasure) }
                }

            _state.update { it.copy(loadingState = LoadingState.Finished) }
        }
    }
}