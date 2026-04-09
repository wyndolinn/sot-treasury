package com.wynndie.sottreasury.sharedCore.presentation.controllers.overlay

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OverlayController {
    private val _overlay = Channel<OverlayType>()
    val overlay = _overlay.receiveAsFlow()

    suspend fun send(overlayType: OverlayType) {
        _overlay.send(overlayType)
    }
}