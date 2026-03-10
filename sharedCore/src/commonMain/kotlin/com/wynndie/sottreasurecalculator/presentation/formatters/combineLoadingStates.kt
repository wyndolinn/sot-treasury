package com.wynndie.sottreasurecalculator.presentation.formatters

import com.wynndie.sottreasurecalculator.sharedResources.Res
import com.wynndie.sottreasurecalculator.sharedResources.error_unknown

internal fun combineLoadingStates(
    vararg states: LoadingState
): LoadingState {
    return when {
        states.any { it is LoadingState.Loading } -> LoadingState.Loading

        states.any { it is LoadingState.Failed } -> {
            val failedLoadingState = states.firstOrNull { it is LoadingState.Failed } as? LoadingState.Failed
            val error = failedLoadingState?.message ?: UiText.ResourceString(Res.string.error_unknown)
            LoadingState.Failed(error)
        }

        states.all { it is LoadingState.Finished } -> LoadingState.Finished

        else -> LoadingState.Loading
    }
}