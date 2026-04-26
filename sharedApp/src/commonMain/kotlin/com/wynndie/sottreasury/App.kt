package com.wynndie.sottreasury

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.wynndie.sottreasury.navigation.RootNavHost
import com.wynndie.sottreasury.navigation.Route
import com.wynndie.sottreasury.sharedCore.presentation.components.effects.ObserveAsEvents
import com.wynndie.sottreasury.sharedCore.presentation.controllers.overlay.OverlayController
import com.wynndie.sottreasury.sharedCore.presentation.controllers.overlay.OverlayType
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
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
            RootNavHost(
                startDestination = Route.MainNavGraph,
                modifier = Modifier
            )
        }
    }
}