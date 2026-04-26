package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.wynndie.sottreasury.sharedCore.presentation.extensions.getInitials
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizes
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components.AsyncImage

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
                    .size(MaterialTheme.sizes.medium)
                    .clip(CircleShape)
                    .background(Color("FF2C3134".hexToLong()))
            ) {
                AsyncImage(
                    url = icon,
                    contentDescription = label,
                    error = label.getInitials(),
                    errorStyle = MaterialTheme.typography.labelMedium,
                    errorColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(MaterialTheme.sizes.extraSmall)
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