package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

class ChangeTreasureAmountUseCase {
    operator fun invoke(
        id: Int,
        oldAmount: Int,
        newAmount: Int,
        allTreasure: Map<Int,  Treasure>,
        valuePerFaction: Map<Int, Map<Int, Pair<Int, Int>>>
    ): Map<Int, Map<Int, Pair<Int, Int>>> {

        val newValuePerFaction = valuePerFaction.toMutableMap()
        val treasure = allTreasure[id] ?: return valuePerFaction
        val factionId = treasure.factions.first()
        val valuesMap = newValuePerFaction[factionId].orEmpty().toMutableMap()

        treasure.values.forEach { value ->
            val current = valuesMap[value.id] ?: Pair(0, 0)
            valuesMap[value.id] = Pair(
                current.first + value.minPrice * (newAmount - oldAmount),
                current.second + value.maxPrice * (newAmount - oldAmount)
            )
        }

        newValuePerFaction[factionId] = valuesMap
        return newValuePerFaction
    }
}