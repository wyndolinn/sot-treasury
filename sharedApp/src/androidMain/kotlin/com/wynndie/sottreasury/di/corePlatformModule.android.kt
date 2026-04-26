package com.wynndie.sottreasury.di

import com.wynndie.sottreasury.database.AppDatabaseFactory
import com.wynndie.sottreasury.datastore.AppDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appPlatformModule: Module = module {
    single { AppDatabaseFactory(androidApplication()) }
    single { AppDataStoreFactory(androidApplication()) }
}