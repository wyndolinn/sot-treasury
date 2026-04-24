package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.extensions.tileShadow
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant

@Composable
fun TreasureTileVariant(
    title: String,
    treasure: List<Treasure>,
    amounts: Map<Int, Int>,
    onChangeAmount: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val hasTreasure = amounts.filter {
        it.key in treasure.map { item -> item.id }
    }.any { it.value > 0 }

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
            .border(
                width = if (hasTreasure) 2.dp else 1.dp,
                color =  if (hasTreasure) {
                    MaterialTheme.colorScheme.primaryContainer
                } else MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        treasure.forEach { item ->
            val amount = amounts[item.id] ?: 0
            TreasureLayout(
                name = item.name,
                amount = amount,
                values = item.values,
                onChangeAmount = { onChangeAmount(item.id, it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureTilePreview() {
    AppTheme {
        val variant = Variant(
            id = 0,
            name = "Cooked",
            icon = "",
            treasure = listOf(
                Treasure(
                    id = 0,
                    name = "Potato",
                    sellableTo = emptyList(),
                    values = listOf(
                        TreasureValue(0, "Gold", "", 600, 800),
                        TreasureValue(1, "EmissaryValue", "", 1000, 1000)
                    )
                ),
                Treasure(
                    id = 1,
                    name = "Carrot",
                    sellableTo = emptyList(),
                    values = listOf(
                        TreasureValue(0, "Gold", "", 600, 800),
                        TreasureValue(1, "EmissaryValue", "", 1000, 1000)
                    )
                ),
                Treasure(
                    id = 2,
                    name = "Cabbage",
                    sellableTo = emptyList(),
                    values = listOf(
                        TreasureValue(0, "Gold", "", 600, 800),
                        TreasureValue(1, "EmissaryValue", "", 1000, 1000)
                    )
                )
            )
        )

        TreasureTileVariant(
            title = variant.name,
            treasure = variant.treasure,
            amounts = mapOf(0 to 0, 1 to 3, 2 to 7),
            onChangeAmount = { _, _ -> },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}