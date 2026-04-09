package com.wynndie.sottreasury.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.dao.TreasureDao
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.EmissaryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.FactionEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.SubcategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureValueEntity

@Database(
    entities = [
        TreasureEntity::class,
        TreasureValueEntity::class,
        FactionEntity::class,
        CategoryEntity::class,
        SubcategoryEntity::class,
        EmissaryEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val treasureDao: TreasureDao

    companion object {
        const val DB_NAME = "app_database.db"
    }
}