package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.di

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.events.EventsViewModel
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val calculatorModule: Module = module {
    viewModelOf(::TreasureViewModel)
    viewModelOf(::EventsViewModel)
}