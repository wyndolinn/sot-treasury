package com.wynndie.sottreasury.di

import com.wynndie.sottreasury.database.AppDatabaseFactory
import com.wynndie.sottreasury.datastore.AppDataStoreFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val appPlatformModule: Module = module {
    singleOf(::AppDatabaseFactory)
    singleOf(::AppDataStoreFactory)
}