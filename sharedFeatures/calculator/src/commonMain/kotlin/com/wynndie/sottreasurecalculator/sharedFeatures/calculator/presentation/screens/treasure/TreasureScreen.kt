package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasurecalculator.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.OnChangeFactionPage
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.OnChangeTreasureCount
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.OnClickSubcategory
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.components.TreasureCategory

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TreasureScreenRoot(
    viewModel: TreasureViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Crossfade(targetState = state.loadingState) { loadingState ->
        when (loadingState) {
            LoadingState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize()
                ) {
                    LoadingIndicator()
                }
            }

            is LoadingState.Failed -> {

            }

            LoadingState.Finished -> {
                TreasureScreen(
                    state = state,
                    onAction = viewModel::onAction,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun TreasureScreen(
    state: TreasureState,
    onAction: (TreasureAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState { state.treasure.size }

    LaunchedEffect(state.selectedFactionPage) {
        pagerState.animateScrollToPage(state.selectedFactionPage)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        onAction(OnChangeFactionPage(pagerState.targetPage))
    }


    Column(
        modifier = modifier
    ) {
        PrimaryScrollableTabRow(
            selectedTabIndex = state.selectedFactionPage,
            edgePadding = 0.dp
        ) {
            state.treasure.forEachIndexed { index, faction ->
                val selected = index == state.selectedFactionPage
                Tab(
                    selected = selected,
                    onClick = { onAction(OnChangeFactionPage(index)) },
                    text = {
                        Text(
                            text = faction.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight(if (selected) 600 else 400),
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = MaterialTheme.spacing.medium,
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val faction = state.treasure[pageIndex]
                faction.categories.forEach { category ->
                    TreasureCategory(
                        category = category,
                        treasureAmounts = state.treasureAmounts,
                        selectedSubcategory = state.selectedSubcategories[faction.id]?.get(category.id)
                            ?: 0,
                        onClickSubcategory = {
                            onAction(
                                OnClickSubcategory(
                                    faction.id,
                                    category.id,
                                    it
                                )
                            )
                        },
                        onChangeAmount = { treasureId, amount ->
                            onAction(OnChangeTreasureCount(treasureId, amount))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                    )
                }
            }
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