package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.EmptyOutcome
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary
import kotlinx.coroutines.flow.Flow

interface TreasureRepository {
    suspend fun syncTreasure(): EmptyOutcome<DataError.Remote>
    suspend fun syncEmissaries(): EmptyOutcome<DataError.Remote>

    fun getTreasure(): Flow<List<Faction>>
    fun getEmissaries(): Flow<List<Emissary>>
}