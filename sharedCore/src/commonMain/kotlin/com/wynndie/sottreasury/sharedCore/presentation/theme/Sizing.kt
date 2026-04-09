package com.wynndie.sottreasury.sharedCore.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizing(
    val extraSmall: Dp = 24.dp,
    val small: Dp = 32.dp,
    val medium: Dp = 40.dp,
    val large: Dp = 48.dp,
    val extraLarge: Dp = 56.dp
)

val LocalSizing = compositionLocalOf { Sizing() }
val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current