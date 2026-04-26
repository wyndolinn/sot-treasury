package com.wynndie.sottreasury.sharedFeatures.calculator.domain.repositories

import com.wynndie.sottreasury.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasury.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue
import kotlinx.coroutines.flow.Flow

interface TreasureRepository {
    suspend fun syncData(): EmptyOutcome<DataError.Remote>

    fun getTreasure(): Flow<Map<Int, Faction>>
    fun getCurrencies(): Flow<List<TreasureValue>>
    fun getEmissaries(): Flow<List<Emissary>>
}