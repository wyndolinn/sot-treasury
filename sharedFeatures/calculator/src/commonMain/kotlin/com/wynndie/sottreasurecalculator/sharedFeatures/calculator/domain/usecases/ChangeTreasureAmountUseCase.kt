package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

class ChangeTreasureAmountUseCase {
    operator fun invoke(
        treasureId: Int,
        oldAmount: Int,
        newAmount: Int,
        allTreasure: Map<Int, Treasure>,
        valuePerEmissary: Map<Int, Map<Int, Pair<Int, Int>>>
    ): Map<Int, Map<Int, Pair<Int, Int>>> {

        val newValuePerEmissary = valuePerEmissary.toMutableMap()
        val treasure = allTreasure[treasureId] ?: return valuePerEmissary

        val emissaryId = treasure.sellableTo.first()
        val valuesMap = newValuePerEmissary[emissaryId].orEmpty().toMutableMap()
        val amountDifference = newAmount - oldAmount

        treasure.values.forEach { value ->
            val current = valuesMap[value.currencyId] ?: Pair(0, 0)
            valuesMap[value.currencyId] = Pair(
                current.first + (value.minPrice ?: 0) * amountDifference,
                current.second + (value.maxPrice ?: 0) * amountDifference
            )
        }

        newValuePerEmissary[emissaryId] = valuesMap
        return newValuePerEmissary
    }
}