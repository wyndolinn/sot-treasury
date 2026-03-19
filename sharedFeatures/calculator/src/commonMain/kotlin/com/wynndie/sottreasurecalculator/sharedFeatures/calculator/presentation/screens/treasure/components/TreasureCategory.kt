package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions.thenIf
import com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions.throttleClick
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Category
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.components.TreasureTile

@Composable
fun TreasureCategory(
    category: Category,
    selectedSubcategory: Int,
    treasureAmounts: Map<Int, Int>,
    onClickSubcategory: (Int) -> Unit,
    onChangeAmount: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight(600)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier.fillMaxWidth()
        ) {
            category.subcategories.forEach { subcategory ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .thenIf(selectedSubcategory == category.subcategories.indexOf(subcategory)) {
                            Modifier.border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.small
                            )
                        }
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .throttleClick {
                            onClickSubcategory(category.subcategories.indexOf(subcategory))
                        }
                        .padding(MaterialTheme.spacing.small)
                ) {
                    Text(
                        text = subcategory.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight(600)
                    )
                }
            }
        }

        Crossfade(selectedSubcategory) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                category.subcategories.getOrNull(selectedSubcategory)?.let { subcategory ->
                    subcategory.items.forEach { item ->
                        val amount = treasureAmounts[item.id] ?: 0
                        TreasureTile(
                            title = item.name,
                            currencies = item.currencies,
                            amount = amount,
                            onClickIncrement = { onChangeAmount(item.id, amount + 1) },
                            onClickDecrement = { onChangeAmount(item.id, amount - 1) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}