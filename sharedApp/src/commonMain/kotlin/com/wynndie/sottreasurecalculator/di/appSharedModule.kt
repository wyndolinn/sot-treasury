package com.wynndie.sottreasurecalculator.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.wynndie.sottreasurecalculator.database.AppDatabase
import com.wynndie.sottreasurecalculator.database.AppDatabaseFactory
import com.wynndie.sottreasurecalculator.datastore.AppDataStoreFactory
import com.wynndie.sottreasurecalculator.sharedCore.data.remote.HttpClientFactory
import org.koin.dsl.module

val appSharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<AppDatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<AppDataStoreFactory>().create() }

    single { get<AppDatabase>().treasureDao }
}