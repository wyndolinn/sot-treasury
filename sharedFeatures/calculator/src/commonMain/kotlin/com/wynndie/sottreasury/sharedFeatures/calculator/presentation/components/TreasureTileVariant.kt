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
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Variant

@Composable
fun TreasureTileVariant(
    title: String,
    treasure: List<Treasure>,
    amounts: Map<Int, Int>,
    onChangeAmount: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(600)
        )

        treasure.forEach { item ->
            val amount = amounts[item.id] ?: 0
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(600)
            )

            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    item.values.forEach { currency ->
                        TreasureValue(currency)
                    }
                }

                AmountChanger(
                    amount = amount,
                    onIncrement = { onChangeAmount(item.id, amount + 1) },
                    onDecrement = { onChangeAmount(item.id, amount - 1) }
                )
            }
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