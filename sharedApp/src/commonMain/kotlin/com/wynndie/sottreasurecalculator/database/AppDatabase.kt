package com.wynndie.sottreasurecalculator.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.wynndie.sottreasurecalculator.sharedCore.data.local.entities.TreasureEntity

@Database(
    entities = [TreasureEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "app_database.db"
    }
}