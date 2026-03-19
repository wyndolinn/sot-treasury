package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction

interface TreasureRepository {
    suspend fun loadTreasure(): Outcome<List<Faction>, DataError.Remote>
}