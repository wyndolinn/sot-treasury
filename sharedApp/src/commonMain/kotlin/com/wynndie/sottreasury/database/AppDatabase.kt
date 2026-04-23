package com.wynndie.sottreasury.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.dao.TreasureDao
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.EmissaryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.FactionEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.SubcategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureValueEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.VariantEntity

@Database(
    entities = [
        TreasureEntity::class,
        TreasureValueEntity::class,
        FactionEntity::class,
        CategoryEntity::class,
        SubcategoryEntity::class,
        VariantEntity::class,
        EmissaryEntity::class
    ],
    version = 2
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val treasureDao: TreasureDao

    companion object {
        const val DB_NAME = "app_database.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS variantentity (
                            id INTEGER NOT NULL,
                            name TEXT NOT NULL,
                            icon TEXT NOT NULL,
                            PRIMARY KEY(id)
                        )
                    """.trimIndent()
                )

                connection.execSQL(
                    """
                        ALTER TABLE treasureentity
                        ADD COLUMN variant TEXT NOT NULL
                    """.trimIndent()
                )
            }
        }
    }
}