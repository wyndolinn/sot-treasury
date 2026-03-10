package com.wynndie.sottreasurecalculator.di

import com.wynndie.sottreasurecalculator.data.database.AppDatabaseFactory
import com.wynndie.sottreasurecalculator.data.datastore.AppDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val corePlatformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf(::AppDatabaseFactory)
    singleOf(::AppDataStoreFactory)
}