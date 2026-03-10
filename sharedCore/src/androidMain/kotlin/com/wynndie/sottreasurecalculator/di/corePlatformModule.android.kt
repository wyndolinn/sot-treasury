package com.wynndie.sottreasurecalculator.di

import com.wynndie.sottreasurecalculator.data.database.AppDatabaseFactory
import com.wynndie.sottreasurecalculator.data.datastore.AppDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val corePlatformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { AppDatabaseFactory(androidApplication()) }
    single { AppDataStoreFactory(androidApplication()) }
}