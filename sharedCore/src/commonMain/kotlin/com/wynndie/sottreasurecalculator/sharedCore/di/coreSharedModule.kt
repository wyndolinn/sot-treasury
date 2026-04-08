package com.wynndie.sottreasurecalculator.sharedCore.di

import com.wynndie.sottreasurecalculator.sharedCore.presentation.controllers.navigation.NavController
import com.wynndie.sottreasurecalculator.sharedCore.presentation.controllers.overlay.OverlayController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreSharedModule = module {
    singleOf(::OverlayController)
    singleOf(::NavController)
}