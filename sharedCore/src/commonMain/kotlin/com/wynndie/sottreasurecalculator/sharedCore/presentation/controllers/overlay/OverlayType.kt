package com.wynndie.sottreasurecalculator.sharedCore.presentation.controllers.overlay

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.Alignment
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.UiText

sealed interface OverlayType {
    data class Snackbar(
        val message: UiText,
        val actionLabel: UiText? = null,
        val withDismissAction: Boolean = true,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val alignment: Alignment.Vertical = Alignment.Bottom
    ) : OverlayType
}