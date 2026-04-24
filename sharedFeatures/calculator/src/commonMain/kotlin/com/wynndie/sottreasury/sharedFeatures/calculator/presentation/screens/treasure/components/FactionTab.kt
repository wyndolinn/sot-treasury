package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.SubcomposeAsyncImage
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizes
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing

@Composable
fun FactionTab(
    icon: String,
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onSelect,
        icon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.extraSmall)
                    .size(MaterialTheme.sizes.medium)
                    .clip(CircleShape)
                    .background(Color("FF2C3134".hexToLong()))
            ) {
                SubcomposeAsyncImage(
                    model = icon,
                    contentDescription = null,
                    success = {
                        Image(
                            painter = it.painter,
                            contentDescription = null,
                            modifier = Modifier.padding(MaterialTheme.spacing.small)
                        )
                    },
                    error = {
                        Text(
                            text = label.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        },
        text = {
            Text(
                text = label,
                style = if (selected) {
                    MaterialTheme.typography.labelMedium
                } else MaterialTheme.typography.labelSmall
            )
        }
    )
}