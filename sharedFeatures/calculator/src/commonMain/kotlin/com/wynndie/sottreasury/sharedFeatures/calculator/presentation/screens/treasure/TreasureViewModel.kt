package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wynndie.sottreasury.sharedCore.domain.outcome.getOrElse
import com.wynndie.sottreasury.sharedCore.presentation.extensions.asUiText
import com.wynndie.sottreasury.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories.TreasureRepository
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.usecases.ChangeEmissaryUseCase
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.usecases.ChangeTreasureAmountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
        viewModelScope.launch {
            val treasure = treasureRepository.getTreasure().first()
            val emissaries = treasureRepository.getEmissaries().first()
            if (treasure.isEmpty() || emissaries.isEmpty()) syncData()
        }

        combine(
            _state.map { it.selectedEmissary },
            _state.map { it.emissaryMultiplier },
            _state.map { it.valuePerEmissary }
        ) { selectedEmissary, emissaryMultiplier, valuePerEmissary ->
            val totalValues = valuePerEmissary.entries
                .flatMap { (emissaryId, valuesMap) ->
                    val multiplier = if (emissaryId == selectedEmissary) {
                        emissaryMultiplier
                    } else 1f

                    valuesMap.map { (currencyId, value) ->
                        val minValue = (value.first * multiplier).toInt()
                        val maxValue = (value.second * multiplier).toInt()
                        currencyId to (minValue to maxValue)
                    }
                }
                .groupBy { it.first }
                .mapValues { (_, entries) ->
                    val totalMin = entries.sumOf { it.second.first }
                    val totalMax = entries.sumOf { it.second.second }
                    totalMin to totalMax
                }
            _state.update {
                it.copy(totalValues = totalValues)
            }
        }.launchIn(viewModelScope)

        collectData()
    }


    fun onAction(action: TreasureAction) {
        when (action) {
            TreasureAction.ReloadData -> syncData()
            TreasureAction.GoOffline -> goOffline()
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


    private fun collectData() {
        combine(
            treasureRepository.getTreasure(),
            treasureRepository.getEmissaries(),
            treasureRepository.getCurrencies()
        ) { treasure, emissaries, currencies ->

            allEmissaries = emissaries.associateBy { it.id }
            allTreasure = treasure
                .asSequence()
                .flatMap { it.value.categories.values }
                .flatMap { it.subcategories.values }
                .flatMap { it.variants.values }
                .flatMap { it.treasure.values }
                .associateBy { it.id }

            _state.update {
                it.copy(
                    factions = treasure,
                    emissaries = emissaries,
                    currencies = currencies
                )
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun syncData() {
        viewModelScope.launch {
            _state.update { it.copy(loadingState = LoadingState.Loading) }

            treasureRepository.syncData().getOrElse { error ->
                _state.update { it.copy(loadingState = LoadingState.Failed(error.asUiText())) }
                return@launch
            }

            _state.update { it.copy(loadingState = LoadingState.Finished) }
        }
    }

    private fun goOffline() {
        _state.update {
            it.copy(loadingState = LoadingState.Finished)
        }
    }

    private fun selectSubcategory(factionId: Int, categoryId: Int, subcategoryIndex: Int) {
        _state.update { state ->
            val selectedSubcategories = state.selectedSubcategories.toMutableMap().apply {
                val categories = getOrElse(factionId) { mapOf() }.toMutableMap()
                if (subcategoryIndex > 0) {
                    categories[categoryId] = subcategoryIndex
                } else categories.remove(categoryId)

                if (categories.isNotEmpty()) put(factionId, categories) else remove(factionId)
            }

            state.copy(selectedSubcategories = selectedSubcategories)
        }
    }

    private fun changeTreasureAmount(id: Int, amount: Int) {
        _state.update { state ->
            val treasureAmounts = state.treasureAmounts.toMutableMap().apply {
                if (amount > 0) put(id, amount) else remove(id)
            }
            val valuePerEmissary = changeTreasureAmountUseCase(
                treasureId = id,
                oldAmount = state.treasureAmounts[id] ?: 0,
                newAmount = amount,
                allTreasure = allTreasure,
                valuePerEmissary = state.valuePerEmissary,
                selectedEmissaryId = state.selectedEmissary
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
                emissaryMultiplier = emissaryMultiplier,
                isEmissaryPickerOpen = false
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