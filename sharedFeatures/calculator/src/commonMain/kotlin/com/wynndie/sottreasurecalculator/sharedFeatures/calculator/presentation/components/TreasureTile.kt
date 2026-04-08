package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions.formatAsAmount
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.sizing
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureValue
import com.wynndie.sottreasurecalculator.sharedResources.Res
import com.wynndie.sottreasurecalculator.sharedResources.ic_add
import com.wynndie.sottreasurecalculator.sharedResources.ic_minus
import org.jetbrains.compose.resources.painterResource

@Composable
fun TreasureTile(
    title: String,
    currencies: List<TreasureValue>,
    amount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
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

        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                currencies.forEach { currency ->
                    TreasureValue(currency)
                }
            }

            AmountChanger(
                amount = amount,
                onIncrement = onIncrement,
                onDecrement = onDecrement
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureTilePreview() {
    AppTheme {
        TreasureTile(
            title = "Captain's Chest",
            currencies = listOf(
                TreasureValue(0, "Gold", "", 600, 800),
                TreasureValue(1, "EmissaryValue", "", 1000, 1000),
            ),
            amount = 3,
            onIncrement = { },
            onDecrement = { },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}