package com.wynndie.sottreasury.sharedCore.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes(
    val extraSmall: Dp = 24.dp,
    val small: Dp = 32.dp,
    val medium: Dp = 40.dp,
    val large: Dp = 48.dp,
    val extraLarge: Dp = 56.dp
)

val LocalSizes = compositionLocalOf { Sizes() }
val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalSizes.current