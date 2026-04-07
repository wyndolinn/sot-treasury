package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.sizing
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.domain.models.Emissary
import kotlin.math.roundToInt

@Composable
fun EmissarySelector(
    emissaries: List<Emissary>,
    selectedEmissary: Int,
    selectedEmissaryGrade: Int,
    isEmissaryPickerOpen: Boolean,
    onToggleEmissaryPicker: (Boolean) -> Unit,
    onSelectEmissary: (Int) -> Unit,
    onSelectEmissaryGrade: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    emissaries.getOrNull(selectedEmissary)?.let { emissary ->
        Row(
            modifier = modifier
        ) {
            val primaryColor = if (emissary.color.isNotBlank()) {
                Color(emissary.color.hexToLong())
            } else Color.Gray
            val backgroundColor = primaryColor.copy(
                red = primaryColor.red * 0.3f,
                green = primaryColor.green * 0.3f,
                blue = primaryColor.blue * 0.3f
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(MaterialTheme.sizing.large)
                    .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                    .background(backgroundColor)
                    .clickable { onToggleEmissaryPicker(true) }
            ) {
                SubcomposeAsyncImage(
                    model = emissary.icon,
                    contentDescription = null,
                    error = {
                        Text(
                            text = emissary.name.split(" ").map { it.first() }.joinToString(""),
                            color = primaryColor,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }

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
                value = selectedEmissaryGrade.toFloat(),
                onValueChange = { onSelectEmissaryGrade(it.roundToInt()) },
                valueRange = 0f..emissary.grades.lastIndex.toFloat(),
                steps = emissaries[selectedEmissary].grades.size - 2,
                enabled = selectedEmissary != 0,
                colors = SliderDefaults.colors(
                    thumbColor = primaryColor,
                    activeTrackColor = primaryColor,
                    inactiveTrackColor = backgroundColor,
                    activeTickColor = backgroundColor,
                    inactiveTickColor = primaryColor,
                    disabledActiveTrackColor = primaryColor,
                    disabledInactiveTrackColor = backgroundColor,
                    disabledActiveTickColor = backgroundColor,
                    disabledInactiveTickColor = primaryColor,
                ),
                interactionSource = interactionSource,
                modifier = Modifier
                    .background(
                        color = Color("FF2C3134".hexToLong()),
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    )
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .weight(1f)
            )
        }
    }
}