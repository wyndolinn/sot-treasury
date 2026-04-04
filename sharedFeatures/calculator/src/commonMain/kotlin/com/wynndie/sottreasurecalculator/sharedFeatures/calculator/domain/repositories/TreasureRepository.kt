package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.repositories

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Outcome
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Faction
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary

interface TreasureRepository {
    suspend fun loadTreasure(): Outcome<List<Faction>, DataError.Remote>
    suspend fun loadEmissaries(): Outcome<List<Emissary>, DataError.Remote>
}