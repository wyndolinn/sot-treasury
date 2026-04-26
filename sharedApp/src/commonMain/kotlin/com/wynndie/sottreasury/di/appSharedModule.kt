package com.wynndie.sottreasury.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.wynndie.sottreasury.database.AppDatabase
import com.wynndie.sottreasury.database.AppDatabaseFactory
import com.wynndie.sottreasury.datastore.AppDataStoreFactory
import com.wynndie.sottreasury.sharedCore.data.remote.HttpClientFactory
import org.koin.dsl.module

val appSharedModule = module {
    single { HttpClientFactory.create(get()) }
    single {
        get<AppDatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .addMigrations(
                AppDatabase.MIGRATION_1_2,
                AppDatabase.MIGRATION_2_3,
                AppDatabase.MIGRATION_3_4
            )
            .build()
    }
    single { get<AppDataStoreFactory>().create() }

    single { get<AppDatabase>().treasureDao }
}