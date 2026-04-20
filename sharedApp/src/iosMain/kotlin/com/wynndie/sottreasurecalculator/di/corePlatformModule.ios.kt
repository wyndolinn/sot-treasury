package com.wynndie.sottreasurecalculator.di

import com.wynndie.sottreasurecalculator.database.AppDatabaseFactory
import com.wynndie.sottreasurecalculator.datastore.AppDataStoreFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val appPlatformModule: Module = module {
    singleOf(::AppDatabaseFactory)
    singleOf(::AppDataStoreFactory)
}