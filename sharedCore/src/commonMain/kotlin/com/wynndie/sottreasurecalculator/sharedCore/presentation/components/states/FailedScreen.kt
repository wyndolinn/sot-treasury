package com.wynndie.sottreasurecalculator.sharedCore.presentation.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedResources.Res
import com.wynndie.sottreasurecalculator.sharedResources.go_offline
import com.wynndie.sottreasurecalculator.sharedResources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun FailedScreen(
    message: String,
    onRetry: () -> Unit,
    onOffline: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.small,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onRetry
        ) {
            Text(text = stringResource(Res.string.retry))
        }

        onOffline?.let {
            TextButton(
                onClick = it
            ) {
                Text(text = stringResource(Res.string.go_offline))
            }
        }
    }
}