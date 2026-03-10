package com.wynndie.sottreasurecalculator.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.wynndie.sottreasurecalculator.data.database.AppDatabaseFactory
import com.wynndie.sottreasurecalculator.data.datastore.AppDataStoreFactory
import com.wynndie.sottreasurecalculator.data.remote.HttpClientFactory
import com.wynndie.sottreasurecalculator.presentation.controllers.navigation.NavController
import com.wynndie.sottreasurecalculator.presentation.controllers.overlay.OverlayController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreSharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<AppDatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<AppDataStoreFactory>().create() }

    singleOf(::OverlayController)
    singleOf(::NavController)
}