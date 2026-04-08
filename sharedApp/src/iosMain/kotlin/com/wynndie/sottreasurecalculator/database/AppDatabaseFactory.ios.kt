package com.wynndie.sottreasurecalculator.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.wynndie.sottreasurecalculator.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class AppDatabaseFactory {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = documentDirectory() + "/${AppDatabase.DB_NAME}"
        return Room.databaseBuilder<AppDatabase>(name = dbFile)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}