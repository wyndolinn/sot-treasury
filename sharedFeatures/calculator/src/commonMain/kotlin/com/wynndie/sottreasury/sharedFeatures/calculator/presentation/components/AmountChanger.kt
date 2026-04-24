package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.extensions.formatAsAmount
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizes
import com.wynndie.sottreasury.sharedResources.Res
import com.wynndie.sottreasury.sharedResources.ic_add
import com.wynndie.sottreasury.sharedResources.ic_minus
import org.jetbrains.compose.resources.painterResource

@Composable
fun AmountChanger(
    onIncrement: () -> Unit,
    amount: Int,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        IconButton(
            onClick = onDecrement,
            shape = MaterialTheme.shapes.small,
            enabled = amount > 0,
            colors = IconButtonDefaults.iconButtonColors().copy(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.size(MaterialTheme.sizes.medium)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_minus),
                contentDescription = null
            )
        }

        Text(
            text = amount.toString().formatAsAmount(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(min = MaterialTheme.sizes.small)
        )

        IconButton(
            onClick = onIncrement,
            shape = MaterialTheme.shapes.small,
            colors = IconButtonDefaults.iconButtonColors().copy(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.size(MaterialTheme.sizes.medium)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = null
            )
        }
    }
}