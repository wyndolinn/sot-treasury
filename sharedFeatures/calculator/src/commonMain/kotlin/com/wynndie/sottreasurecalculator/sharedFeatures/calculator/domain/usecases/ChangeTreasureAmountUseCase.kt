package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

class ChangeTreasureAmountUseCase {
    operator fun invoke(
        treasureId: Int,
        oldAmount: Int,
        newAmount: Int,
        selectedEmissaryId: Int,
        allTreasure: Map<Int, Treasure>,
        valuePerEmissary: Map<Int, Map<Int, Pair<Int, Int>>>
    ): Map<Int, Map<Int, Pair<Int, Int>>> {

        val newValuePerEmissary = valuePerEmissary.toMutableMap()
        val treasure = allTreasure[treasureId] ?: return valuePerEmissary

        val targetEmissaryId = treasure.sellableTo
            .find { it == selectedEmissaryId }
            ?: treasure.sellableTo.first()
        val valuesMap = newValuePerEmissary[targetEmissaryId].orEmpty().toMutableMap()
        val amountDifference = newAmount - oldAmount

        treasure.values.forEach { value ->
            if (value.currencyId == 0) {
                if (targetEmissaryId != selectedEmissaryId) return@forEach
            }
            val current = valuesMap[value.currencyId] ?: Pair(0, 0)
            val minValue = current.first + (value.minPrice ?: 0) * amountDifference
            val maxValue = current.second + (value.maxPrice ?: 0) * amountDifference
            if (minValue > 0 || maxValue > 0) {
                valuesMap[value.currencyId] = Pair(minValue, maxValue)
            } else valuesMap.remove(value.currencyId)
        }

        if (valuesMap.isNotEmpty()) {
            newValuePerEmissary[targetEmissaryId] = valuesMap
        } else newValuePerEmissary.remove(targetEmissaryId)

        return newValuePerEmissary
    }
}