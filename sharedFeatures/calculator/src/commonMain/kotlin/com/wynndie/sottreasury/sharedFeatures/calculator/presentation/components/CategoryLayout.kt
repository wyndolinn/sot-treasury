package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Category

@Composable
fun CategoryLayout(
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
            style = MaterialTheme.typography.titleLarge
        )

        if (category.subcategories.size > 1) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                modifier = Modifier.fillMaxWidth()
            ) {
                category.subcategories.forEachIndexed { index, subcategory ->
                    SubcategoryChip(
                        label = subcategory.name,
                        selected = selectedSubcategory == index,
                        onClickSubcategory = { onClickSubcategory(index) }
                    )
                }
            }
        }

        Crossfade(
            targetState = selectedSubcategory
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                category.subcategories.getOrNull(selectedSubcategory)?.let { subcategory ->
                    subcategory.variants.forEach { variant ->
                        if (variant.id < 0) {
                            variant.treasure.forEach { treasure ->
                                TreasureTile(
                                    treasure = treasure,
                                    amounts = treasureAmounts,
                                    onChangeAmount = { id, amount -> onChangeAmount(id, amount) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            TreasureTileVariant(
                                title = variant.name,
                                treasure = variant.treasure,
                                amounts = treasureAmounts,
                                onChangeAmount = { id, amount -> onChangeAmount(id, amount) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}