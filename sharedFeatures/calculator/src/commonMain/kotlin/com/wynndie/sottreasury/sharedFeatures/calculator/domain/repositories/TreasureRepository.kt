package com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories

import com.wynndie.sottreasury.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasury.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import kotlinx.coroutines.flow.Flow

interface TreasureRepository {
    suspend fun syncTreasure(): EmptyOutcome<DataError.Remote>
    suspend fun syncEmissaries(): EmptyOutcome<DataError.Remote>

    fun getTreasure(): Flow<List<Faction>>
    fun getEmissaries(): Flow<List<Emissary>>
}