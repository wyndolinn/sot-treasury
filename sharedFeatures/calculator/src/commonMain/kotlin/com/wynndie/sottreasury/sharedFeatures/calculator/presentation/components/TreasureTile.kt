package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureTile(
    id: Int,
    name: String,
    values: List<TreasureValue>,
    amount: Int,
    onChangeAmount: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraExtraSmall),
        modifier = modifier
            .border(
                width = if (amount > 0) 2.dp else 1.dp,
                color =  if (amount > 0) {
                    MaterialTheme.colorScheme.primaryContainer
                } else MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(MaterialTheme.spacing.medium)
    ) {
        TreasureLayout(
            name = name,
            amount = amount,
            values = values,
            onChangeAmount = { onChangeAmount(id, it) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureTilePreview() {
    AppTheme {
        TreasureTile(
            id = 0,
            name = "Carrot",
            values = listOf(
                TreasureValue(0, "Gold", "", 600, 800),
                TreasureValue(1, "EmissaryValue", "", 1000, 1000)
            ),
            amount = 2,
            onChangeAmount = { _, _ -> },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}