package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.di

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.TreasureRepositoryImpl
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories.TreasureRepository
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.events.EventsViewModel
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val calculatorModule: Module = module {
    factoryOf(::TreasureRepositoryImpl).bind<TreasureRepository>()

    viewModelOf(::TreasureViewModel)
    viewModelOf(::EventsViewModel)
}