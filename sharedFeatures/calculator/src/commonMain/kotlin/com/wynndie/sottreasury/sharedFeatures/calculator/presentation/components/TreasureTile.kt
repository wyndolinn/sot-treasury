package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Treasure
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureTile(
    treasure: Treasure,
    amounts: Map<Int, Int>,
    onChangeAmount: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val amount = amounts[treasure.id] ?: 0
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = treasure.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(600)
        )

        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                treasure.values.forEach { currency ->
                    TreasureValue(currency)
                }
            }

            AmountChanger(
                amount = amount,
                onIncrement = { onChangeAmount(treasure.id, amount + 1) },
                onDecrement = { onChangeAmount(treasure.id, amount - 1) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureTilePreview() {
    AppTheme {
        TreasureTile(
            treasure = Treasure(
                id = 1,
                name = "Carrot",
                sellableTo = emptyList(),
                values = listOf(
                    TreasureValue(0, "Gold", "", 600, 800),
                    TreasureValue(1, "EmissaryValue", "", 1000, 1000)
                )
            ),
            amounts = mapOf(0 to 0, 1 to 3, 2 to 7),
            onChangeAmount = { _, _ -> },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}