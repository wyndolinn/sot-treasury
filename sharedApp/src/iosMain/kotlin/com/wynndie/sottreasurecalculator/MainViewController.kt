package com.wynndie.sottreasurecalculator

import androidx.compose.ui.window.ComposeUIViewController
import com.wynndie.sottreasurecalculator.di.initKoin
import com.wynndie.sottreasurecalculator.sharedCore.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}