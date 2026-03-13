package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreasureViewModel : ViewModel() {

    private val _state = MutableStateFlow(TreasureState())
    val state = _state.asStateFlow()


    init {
        loadDummyData()
    }


    fun onAction(action: TreasureAction) {
        when (action) {

        }
    }


    private fun loadDummyData() {

    }
}