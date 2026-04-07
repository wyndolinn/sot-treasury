package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
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
    onClickIncrement: () -> Unit,
    onClickDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight(600)
            )

            Column {
                currencies.forEach { currency ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SubcomposeAsyncImage(
                            model = currency.icon,
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
                                    text = "${currency.name}:",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        )

                        val price = if (currency.minPrice != currency.maxPrice) {
                            val minPrice = currency.minPrice.toString().formatAsAmount()
                            val maxPrice = currency.maxPrice.toString().formatAsAmount()
                            "$minPrice–$maxPrice"
                        } else currency.minPrice.toString().formatAsAmount()

                        Text(
                            text = price,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(MaterialTheme.sizing.medium)
                    .clickable(onClick = onClickIncrement)
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_add),
                    contentDescription = null
                )
            }

            Text(
                text = amount.toString().formatAsAmount()
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(MaterialTheme.sizing.medium)
                    .clickable(onClick = onClickDecrement)
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_minus),
                    contentDescription = null
                )
            }
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
                TreasureValue(0, "Gold", "", 600, 800)
            ),
            amount = 3,
            onClickIncrement = { },
            onClickDecrement = { },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}