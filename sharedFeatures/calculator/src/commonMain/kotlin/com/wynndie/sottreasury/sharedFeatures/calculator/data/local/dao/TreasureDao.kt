package com.wynndie.sottreasury.sharedFeatures.calculator.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.EmissaryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.FactionEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.SubcategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureValueEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureWithValues
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.VariantEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant
import kotlinx.coroutines.flow.Flow

@Dao
interface TreasureDao {

    @Transaction
    @Query("SELECT * FROM treasureentity")
    fun getAllTreasure(): Flow<List<TreasureWithValues>>

    @Upsert
    suspend fun insertTreasure(entities: List<TreasureEntity>)

    @Upsert
    suspend fun insertValues(entities: List<TreasureValueEntity>)


    @Query("SELECT * FROM factionentity")
    fun getAllFactions(): Flow<List<FactionEntity>>

    @Upsert
    suspend fun insertFactions(entities: List<FactionEntity>)


    @Query("SELECT * FROM categoryentity")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insertCategories(entities: List<CategoryEntity>)


    @Query("SELECT * FROM subcategoryentity")
    fun getAllSubcategory(): Flow<List<SubcategoryEntity>>

    @Upsert
    suspend fun insertSubcategories(entities: List<SubcategoryEntity>)


    @Query("SELECT * FROM variantentity")
    fun getAllVariants(): Flow<List<VariantEntity>>

    @Upsert
    suspend fun insertVariants(entities: List<VariantEntity>)



    @Query("SELECT * FROM emissaryentity")
    fun getAllEmissaries(): Flow<List<EmissaryEntity>>

    @Upsert
    suspend fun insertEmissaries(entities: List<EmissaryEntity>)
}