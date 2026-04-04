package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions.asUiText
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases.ChangeEmissaryUseCase
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases.ChangeTreasureAmountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreasureViewModel(
    private val treasureRepository: TreasureRepository,
    private val changeTreasureAmountUseCase: ChangeTreasureAmountUseCase,
    private val changeEmissaryUseCase: ChangeEmissaryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TreasureState())
    val state = _state.asStateFlow()

    private var allTreasure: Map<Int, Treasure> = mapOf()
    private var allEmissaries: Map<Int, Emissary> = mapOf()


    init {
        loadData()
    }


    fun onAction(action: TreasureAction) {
        when (action) {
            TreasureAction.ReloadData -> loadData()
            is TreasureAction.SelectEmissary -> selectEmissary(action.id)
            is TreasureAction.SelectFactionPage -> selectFactionPage(action.id)
            is TreasureAction.ToggleEmissaryPicker -> toggleEmissaryPicker(action.open)
            is TreasureAction.SelectEmissaryGrade -> selectEmissaryGrade(action.level)

            is TreasureAction.SelectSubcategory -> {
                selectSubcategory(action.factionId, action.categoryId, action.subcategoryId)
            }

            is TreasureAction.ChangeTreasureAmount -> {
                changeTreasureAmount(action.treasureId, action.amount)
            }
        }
    }


    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(loadingState = LoadingState.Loading) }

            val treasure = treasureRepository.loadTreasure().getOrElse { error ->
                _state.update { it.copy(loadingState = LoadingState.Failed(error.asUiText())) }
                return@launch
            }
            val emissaries = treasureRepository.loadEmissaries().getOrElse { error ->
                _state.update { it.copy(loadingState = LoadingState.Failed(error.asUiText())) }
                return@launch
            }

            allTreasure = treasure
                .flatMap { it.categories }
                .flatMap { it.subcategories }
                .flatMap { it.treasure }
                .associateBy { it.id }

            allEmissaries = emissaries
                .associateBy { it.id }


            _state.update {
                it.copy(
                    factions = treasure,
                    emissaries = emissaries
                )
            }

            _state.update { it.copy(loadingState = LoadingState.Finished) }
        }
    }

    private fun selectSubcategory(factionId: Int, categoryId: Int, subcategoryId: Int) {
        _state.update { state ->
            val selectedSubcategories = state.selectedSubcategories.toMutableMap().apply {
                val categories = getOrElse(factionId) { mapOf() }.toMutableMap()
                categories[categoryId] = subcategoryId
                put(factionId, categories)
            }

            state.copy(selectedSubcategories = selectedSubcategories)
        }
    }

    private fun changeTreasureAmount(id: Int, amount: Int) {
        _state.update { state ->
            val treasureAmounts = state.treasureAmounts.toMutableMap().apply { put(id, amount) }
            val valuePerEmissary = changeTreasureAmountUseCase(
                treasureId = id,
                oldAmount = state.treasureAmounts[id] ?: 0,
                newAmount = amount,
                allTreasure = allTreasure,
                valuePerEmissary = state.valuePerEmissary
            )

            state.copy(
                treasureAmounts = treasureAmounts,
                valuePerEmissary = valuePerEmissary
            )
        }
    }

    private fun selectEmissary(id: Int) {
        _state.update { state ->
            val emissaryMultiplier = allEmissaries[id]?.grades[state.emissaryGrade] ?: 1f
            val valuePerEmissary = changeEmissaryUseCase(
                emissaryId = id,
                emissaryGrade = state.emissaryGrade,
                allTreasure = allTreasure,
                treasureAmounts = state.treasureAmounts
            )

            state.copy(
                selectedEmissary = id,
                valuePerEmissary = valuePerEmissary,
                emissaryMultiplier = emissaryMultiplier
            )
        }
    }

    private fun selectEmissaryGrade(grade: Int) {
        _state.update { state ->
            val emissaryMultiplier = allEmissaries[state.selectedEmissary]?.grades[grade] ?: 1f

            state.copy(
                emissaryGrade = grade,
                emissaryMultiplier = emissaryMultiplier
            )
        }
    }

    private fun selectFactionPage(id: Int) {
        _state.update { it.copy(selectedFactionPage = id) }
    }


    private fun toggleEmissaryPicker(open: Boolean) {
        _state.update { it.copy(isEmissaryPickerOpen = open) }
    }
}