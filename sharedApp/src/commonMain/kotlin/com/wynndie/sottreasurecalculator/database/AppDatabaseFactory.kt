package com.wynndie.sottreasurecalculator.database

import androidx.room.RoomDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppDatabaseFactory {
    fun create(): RoomDatabase.Builder<AppDatabase>
}