package com.wynndie.sottreasury.sharedCore.presentation.controllers.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavController {
    private val _navEvent = Channel<NavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    suspend fun navigate(navEvent: NavEvent) {
        _navEvent.send(navEvent)
    }
}