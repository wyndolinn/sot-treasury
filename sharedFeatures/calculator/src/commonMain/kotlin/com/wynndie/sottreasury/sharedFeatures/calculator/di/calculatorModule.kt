package com.wynndie.sottreasury.sharedFeatures.calculator.di

import com.wynndie.sottreasury.sharedFeatures.calculator.data.TreasureRepositoryImpl
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories.TreasureRepository
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.usecases.ChangeEmissaryUseCase
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.usecases.ChangeTreasureAmountUseCase
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.events.EventsViewModel
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.TreasureViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val calculatorModule: Module = module {
    factoryOf(::TreasureRepositoryImpl).bind<TreasureRepository>()

    factoryOf(::ChangeTreasureAmountUseCase)
    factoryOf(::ChangeEmissaryUseCase)

    viewModelOf(::TreasureViewModel)
    viewModelOf(::EventsViewModel)
}