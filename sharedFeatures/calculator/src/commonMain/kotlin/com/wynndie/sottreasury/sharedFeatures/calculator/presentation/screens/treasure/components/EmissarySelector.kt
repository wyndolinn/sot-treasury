package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.extensions.getInitials
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizes
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components.AsyncImage
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
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
            val primaryColor = Color(emissary.color.hexToLong())
            val backgroundColor = primaryColor.copy(
                red = primaryColor.red * 0.6f,
                green = primaryColor.green * 0.6f,
                blue = primaryColor.blue * 0.6f
            )

            val sliderShape = MaterialTheme.shapes.medium
            val sliderColors = SliderDefaults.colors(
                thumbColor = primaryColor,
                activeTrackColor = primaryColor,
                inactiveTrackColor = backgroundColor,
                activeTickColor = backgroundColor,
                inactiveTickColor = primaryColor,
                disabledActiveTrackColor = primaryColor,
                disabledInactiveTrackColor = backgroundColor,
                disabledActiveTickColor = backgroundColor,
                disabledInactiveTickColor = primaryColor,
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(MaterialTheme.sizes.extraLarge)
                    .clip(
                        RoundedCornerShape(
                            topStart = sliderShape.topStart,
                            bottomStart = sliderShape.bottomStart,
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
                    .background(sliderColors.inactiveTrackColor)
                    .clickable { onToggleEmissaryPicker(true) }
            ) {
                AsyncImage(
                    url = emissary.icon,
                    contentDescription = emissary.name,
                    error = emissary.name.getInitials(),
                    errorStyle = MaterialTheme.typography.titleSmall,
                    errorColor = primaryColor,
                    contentSpacing = 2.dp,
                    modifier = Modifier.size(MaterialTheme.sizes.small)
                )
            }

            DropdownMenu(
                expanded = isEmissaryPickerOpen,
                onDismissRequest = { onToggleEmissaryPicker(false) }
            ) {
                emissaries.forEachIndexed { index, emissary ->
                    val factionColor = Color(emissary.color.hexToLong())
                    val factionBackgroundColor = primaryColor.copy(
                        red = factionColor.red * 0.6f,
                        green = factionColor.green * 0.6f,
                        blue = factionColor.blue * 0.6f
                    )
                    DropdownMenuItem(
                        leadingIcon = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(MaterialTheme.sizes.small)
                                    .clip(CircleShape)
                                    .background(factionBackgroundColor)
                                    .clickable { onToggleEmissaryPicker(true) }
                            ) {
                                AsyncImage(
                                    url = emissary.icon,
                                    contentDescription = emissary.name,
                                    error = emissary.name.getInitials(),
                                    errorStyle = MaterialTheme.typography.labelMedium,
                                    errorColor = factionColor,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }
                        },
                        text = {
                            Text(
                                text = emissary.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
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
                thumb = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(sliderColors.thumbColor)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "x${emissary.grades[selectedEmissaryGrade]}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color("FF2C3134".hexToLong()),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(32.dp)
                        )
                    }
                },
                track = {
                    SliderDefaults.Track(
                        sliderState = it,
                        thumbTrackGapSize = 2.dp,
                        colors = sliderColors
                    )
                },
                colors = sliderColors,
                interactionSource = interactionSource,
                modifier = Modifier
                    .size(MaterialTheme.sizes.extraLarge)
                    .clip(
                        RoundedCornerShape(
                            topEnd = sliderShape.topStart,
                            bottomEnd = sliderShape.bottomStart,
                            topStart = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
                    .background(Color("FF2C3134".hexToLong()))
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .weight(1f)
            )
        }
    }
}