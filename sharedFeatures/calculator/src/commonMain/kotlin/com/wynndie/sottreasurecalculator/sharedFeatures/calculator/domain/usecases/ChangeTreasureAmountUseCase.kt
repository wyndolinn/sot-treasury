package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

class ChangeTreasureAmountUseCase {
    operator fun invoke(
        treasureId: Int,
        oldAmount: Int,
        newAmount: Int,
        allTreasure: Map<Int,  Treasure>,
        valuePerEmissary: Map<Int, Map<Int, Pair<Int, Int>>>
    ): Map<Int, Map<Int, Pair<Int, Int>>> {

        val newValuePerEmissary = valuePerEmissary.toMutableMap()
        val treasure = allTreasure[treasureId] ?: return valuePerEmissary

        val emissaryId = treasure.sellableTo.first()
        val valuesMap = newValuePerEmissary[emissaryId].orEmpty().toMutableMap()
        val amountDifference = newAmount - oldAmount

        treasure.values.forEach { value ->
            val current = valuesMap[value.id] ?: Pair(0, 0)
            valuesMap[value.id] = Pair(
                current.first + value.minPrice * amountDifference,
                current.second + value.maxPrice * amountDifference
            )
        }

        newValuePerEmissary[emissaryId] = valuesMap
        return newValuePerEmissary
    }
}