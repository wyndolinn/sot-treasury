package com.wynndie.sottreasurecalculator

import androidx.compose.ui.window.ComposeUIViewController
import com.wynndie.sottreasurecalculator.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}