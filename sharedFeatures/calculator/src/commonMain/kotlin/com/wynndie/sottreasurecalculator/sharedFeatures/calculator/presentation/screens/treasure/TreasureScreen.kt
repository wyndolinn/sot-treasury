package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing

@Composable
fun TreasureScreenRoot(
    viewModel: TreasureViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    TreasureScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun TreasureScreen(
    state: TreasureState,
    onAction: (TreasureAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text("treasure screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureScreenPreview() {
    AppTheme {
        TreasureScreen(
            state = TreasureState(),
            onAction = { },
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}