package com.wynndie.sottreasury.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.wynndie.sottreasury.sharedCore.presentation.controllers.navigation.NavController
import com.wynndie.sottreasury.sharedCore.presentation.controllers.navigation.NavEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
inline fun <reified T : NavEvent> ObserveNavEvent(
    noinline onEvent: (T) -> Unit
) {
    val navController = koinInject<NavController>()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, onEvent) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                navController.navEvent.filterIsInstance<T>().collect(onEvent)
            }
        }
    }
}