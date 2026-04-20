package com.wynndie.sottreasurecalculator.di

import com.wynndie.sottreasurecalculator.sharedCore.di.corePlatformModule
import com.wynndie.sottreasurecalculator.sharedCore.di.coreSharedModule
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.di.calculatorModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appPlatformModule,
            appSharedModule,
            corePlatformModule,
            coreSharedModule,
            calculatorModule
        )
    }
}