package com.wynndie.sottreasury

import androidx.compose.ui.window.ComposeUIViewController
import com.wynndie.sottreasury.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}