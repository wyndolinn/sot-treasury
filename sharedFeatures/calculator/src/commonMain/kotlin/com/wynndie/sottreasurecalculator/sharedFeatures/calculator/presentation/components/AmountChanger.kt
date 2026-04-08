package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions.formatAsAmount
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.sizing
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedResources.Res
import com.wynndie.sottreasurecalculator.sharedResources.ic_add
import com.wynndie.sottreasurecalculator.sharedResources.ic_minus
import org.jetbrains.compose.resources.painterResource

@Composable
fun AmountChanger(
    onIncrement: () -> Unit,
    amount: Int,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        IconButton(
            onClick = onIncrement,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.size(MaterialTheme.sizing.medium)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = null
            )
        }

        Text(
            text = amount.toString().formatAsAmount(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(600)
        )

        IconButton(
            onClick = onDecrement,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.size(MaterialTheme.sizing.medium)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_minus),
                contentDescription = null
            )
        }
    }
}