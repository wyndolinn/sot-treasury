package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureCurrencyRow(
    currency: TreasureValue,
    min: Int,
    max: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currency.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = if (min == max) "$min" else "$min – $max",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}