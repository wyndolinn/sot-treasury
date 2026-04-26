package com.wynndie.sottreasury.sharedCore.presentation.formatters

sealed interface LoadingState {
    data object Loading : LoadingState
    data object Finished : LoadingState
    data class Failed(val message: UiText) : LoadingState
}