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
            val targetEmissary = treasure.sellableTo
                .find { it == emissaryId }
                ?: treasure.sellableTo.first()
            val valuesMap = valuePerEmissary[targetEmissary].orEmpty().toMutableMap()

            treasure.values.forEach { value ->
                val current = valuesMap[value.id] ?: Pair(0, 0)
                valuesMap[value.id] = Pair(
                    current.first + value.minPrice * amount,
                    current.second + value.maxPrice * amount
                )
            }

            valuePerEmissary[targetEmissary] = valuesMap
        }

        return valuePerEmissary
    }
}