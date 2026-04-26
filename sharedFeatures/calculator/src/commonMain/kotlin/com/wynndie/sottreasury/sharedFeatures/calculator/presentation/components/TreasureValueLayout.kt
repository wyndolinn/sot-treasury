package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.extensions.formatAsAmount
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureValueLayout(
    value: TreasureValue,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraExtraSmall),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            url = value.icon,
            contentDescription = value.name,
            error = "${value.name}:",
            errorStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.size(18.dp)
        )

        val price = if (value.minPrice != value.maxPrice) {
            val minPrice = value.minPrice
            val maxPrice = value.maxPrice
            "$minPrice – $maxPrice"
        } else value.minPrice.toString()

        Text(
            text = price.formatAsAmount(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}