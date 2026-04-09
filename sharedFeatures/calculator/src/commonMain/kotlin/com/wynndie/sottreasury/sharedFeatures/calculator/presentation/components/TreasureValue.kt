package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.Image
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
import coil3.compose.SubcomposeAsyncImage
import com.wynndie.sottreasury.sharedCore.presentation.extensions.formatAsAmount
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureValue(
    value: TreasureValue,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = value.icon,
            contentDescription = null,
            success = {
                Image(
                    painter = it.painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(18.dp)
                )
            },
            error = {
                Text(
                    text = "${value.name}:",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        val price = if (value.minPrice != value.maxPrice) {
            val minPrice = value.minPrice.toString().formatAsAmount()
            val maxPrice = value.maxPrice.toString().formatAsAmount()
            "$minPrice–$maxPrice"
        } else value.minPrice.toString().formatAsAmount()

        Text(
            text = price,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}