package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.*
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.components.TreasureCategory

@Composable
fun TreasureScreenRoot(
    viewModel: TreasureViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    TreasureScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun TreasureScreen(
    state: TreasureState,
    onAction: (TreasureAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        state.treasure.forEach { category ->
            val selectedSubcategory = state.selectedSubcategories[category.id] ?: 0
            TreasureCategory(
                category = category,
                treasureAmounts = state.treasureAmounts,
                selectedSubcategory = selectedSubcategory,
                onClickSubcategory = { onAction(OnClickSubcategory(category.id, it)) },
                onChangeAmount = { treasureKey, amount ->
                    onAction(
                        OnChangeTreasureCount(
                            treasureKey = treasureKey,
                            amount = amount
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TreasureScreenPreview() {
    AppTheme {
        TreasureScreen(
            state = TreasureState(),
            onAction = { },
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium)
        )
    }
}