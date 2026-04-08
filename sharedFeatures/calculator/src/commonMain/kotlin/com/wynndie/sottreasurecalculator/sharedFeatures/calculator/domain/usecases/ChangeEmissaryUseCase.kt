package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.usecases

import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Treasure

class ChangeEmissaryUseCase {
    operator fun invoke(
        emissaryId: Int,
        emissaryGrade: Int,
        allTreasure: Map<Int, Treasure>,
        treasureAmounts: Map<Int, Int>
    ): Map<Int, Map<Int, Pair<Int, Int>>> {
        val valuePerEmissary: MutableMap<Int, Map<Int, Pair<Int, Int>>> = mutableMapOf()

        treasureAmounts.forEach { (treasureId, amount) ->
            val treasure = allTreasure[treasureId] ?: return@forEach
            val targetEmissaryId = treasure.sellableTo
                .find { it == emissaryId }
                ?: treasure.sellableTo.first()
            val valuesMap = valuePerEmissary[targetEmissaryId].orEmpty().toMutableMap()

            treasure.values.forEach { value ->
                if (value.currencyId == 0) {
                    if (targetEmissaryId != emissaryId) return@forEach
                }
                val current = valuesMap[value.currencyId] ?: Pair(0, 0)
                valuesMap[value.currencyId] = Pair(
                    current.first + (value.minPrice ?: 0) * amount,
                    current.second + (value.maxPrice ?: 0) * amount
                )
            }

            valuePerEmissary[targetEmissaryId] = valuesMap
        }

        return valuePerEmissary
    }
}