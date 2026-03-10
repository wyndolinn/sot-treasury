package com.wynndie.sottreasurecalculator

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.wynndie.sottreasurecalculator.presentation.components.effects.ObserveAsEvents
import com.wynndie.sottreasurecalculator.presentation.controllers.overlay.OverlayController
import com.wynndie.sottreasurecalculator.presentation.controllers.overlay.OverlayType
import com.wynndie.sottreasurecalculator.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val overlayController = koinInject<OverlayController>()

    ObserveAsEvents(overlayController.overlay) { overlay ->
        when (overlay) {
            is OverlayType.Snackbar -> {
                scope.launch {
                    val currentSnackbarData = snackbarHostState.currentSnackbarData
                    currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = overlay.message.asStringAsync(),
                        actionLabel = overlay.actionLabel?.asStringAsync(),
                        withDismissAction = overlay.withDismissAction,
                        duration = overlay.duration
                    )
                }
            }
        }
    }

    AppTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(snackbarData = it)
                }
            }
        ) { _ ->

        }
    }
}