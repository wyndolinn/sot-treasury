package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.sizing
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.TreasureValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureSheetContent(
    values: List<TreasureValue>,
    totalValues: Map<Int, Pair<Int, Int>>,
    emissaries: List<Emissary>,
    isEmissaryPickerOpen: Boolean,
    selectedEmissary: Int,
    selectedEmissaryLevel: Int,
    onToggleEmissaryPicker: (Boolean) -> Unit,
    onSelectEmissary: (Int) -> Unit,
    onSelectEmissaryGrade: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = modifier
                .fillMaxWidth()
        ) {
            values.forEach { currency ->
                val (min, max) = totalValues[currency.currencyId] ?: (0 to 0)
                TreasureCurrencyRow(
                    currency = currency,
                    min = min,
                    max = max
                )
            }
        }

        emissaries.getOrNull(selectedEmissary)?.let { emissary ->
            Row {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.large)
                        .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                        .background(emissary.color)
                        .clickable { onToggleEmissaryPicker(true) }
                )

                DropdownMenu(
                    expanded = isEmissaryPickerOpen,
                    onDismissRequest = { onToggleEmissaryPicker(false) }
                ) {
                    emissaries.forEachIndexed { index, faction ->
                        DropdownMenuItem(
                            text = { Text(faction.name) },
                            onClick = { onSelectEmissary(index) }
                        )
                    }
                }

                val interactionSource = remember { MutableInteractionSource() }
                Slider(
                    value = selectedEmissaryLevel.toFloat(),
                    onValueChange = { onSelectEmissaryGrade(it.roundToInt()) },
                    valueRange = 0f..emissary.grades.lastIndex.toFloat(),
                    steps = emissaries[selectedEmissary].grades.size - 2,
                    enabled = selectedEmissary != 0,
                    colors = SliderDefaults.colors(
                        thumbColor = emissaries[selectedEmissary].color,
                        activeTrackColor = emissaries[selectedEmissary].color,
                        inactiveTrackColor = MaterialTheme.colorScheme.surface,
                        inactiveTickColor = MaterialTheme.colorScheme.inverseSurface,
                        activeTickColor = MaterialTheme.colorScheme.inverseSurface,
                    ),
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceDim,
                            shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .weight(1f)
                )
            }
        }
    }
}