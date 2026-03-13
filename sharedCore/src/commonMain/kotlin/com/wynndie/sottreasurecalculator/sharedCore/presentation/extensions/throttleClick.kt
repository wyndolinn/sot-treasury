package com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.throttleClick(
    throttleMs: Long = 300L,
    ignoreCooldown: Boolean = false,
    onClick: () -> Unit
): Modifier = composed {
    var isLocked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    clickable {
        if (ignoreCooldown) {
            onClick()
            return@clickable
        }

        if (!isLocked) {
            isLocked = true
            onClick()
            scope.launch {
                delay(throttleMs)
                isLocked = false
            }
        }
    }
}