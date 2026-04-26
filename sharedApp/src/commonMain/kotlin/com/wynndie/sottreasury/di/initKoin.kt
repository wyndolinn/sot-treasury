package com.wynndie.sottreasury.di

import com.wynndie.sottreasury.sharedCore.di.corePlatformModule
import com.wynndie.sottreasury.sharedCore.di.coreSharedModule
import com.wynndie.sottreasury.sharedFeatures.calculator.di.calculatorModule
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