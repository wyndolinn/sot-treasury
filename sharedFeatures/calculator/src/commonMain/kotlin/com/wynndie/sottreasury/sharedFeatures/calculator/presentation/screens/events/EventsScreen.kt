package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.events

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing

@Composable
fun EventsScreenRoot(
    viewModel: EventsViewModel,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun EventsScreen(
    state: EventsState,
    onAction: (EventsAction) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true)
@Composable
private fun EventsScreenPreview() {
    AppTheme {
        EventsScreen(
            state = EventsState(),
            onAction = {  },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}