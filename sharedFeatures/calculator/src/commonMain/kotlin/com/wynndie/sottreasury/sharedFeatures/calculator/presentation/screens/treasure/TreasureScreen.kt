package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wynndie.sottreasury.sharedCore.presentation.components.states.FailedScreen
import com.wynndie.sottreasury.sharedCore.presentation.components.states.LoadingScreen
import com.wynndie.sottreasury.sharedCore.presentation.formatters.LoadingState
import com.wynndie.sottreasury.sharedCore.presentation.theme.AppTheme
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components.CategoryLayout
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.ChangeTreasureAmount
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.SelectFactionPage
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.TreasureAction.SelectSubcategory
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components.FactionTab
import com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components.TreasureSheetContent

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TreasureScreenRoot(
    viewModel: TreasureViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    BottomSheetScaffold(
        sheetContent = {
            TreasureSheetContent(
                values = state.currencies,
                totalValues = state.totalValues,
                emissaries = state.emissaries,
                isEmissaryPickerOpen = state.isEmissaryPickerOpen,
                selectedEmissary = state.selectedEmissary,
                selectedEmissaryLevel = state.emissaryGrade,
                onToggleEmissaryPicker = { viewModel.onAction(TreasureAction.ToggleEmissaryPicker(it)) },
                onSelectEmissary = { viewModel.onAction(TreasureAction.SelectEmissary(it)) },
                onSelectEmissaryGrade = { viewModel.onAction(TreasureAction.SelectEmissaryGrade(it)) },
                onReloadData = { viewModel.onAction(TreasureAction.ReloadData) }
            )
        }
    ) { innerPadding ->
        Crossfade(targetState = state.loadingState) { loadingState ->
            when (loadingState) {
                LoadingState.Loading -> {
                    LoadingScreen(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                is LoadingState.Failed -> {
                    FailedScreen(
                        message = loadingState.message.asString(),
                        onRetry = { viewModel.onAction(TreasureAction.ReloadData) },
                        onOffline = if (!state.factions.isEmpty() && !state.emissaries.isEmpty()) {
                            { viewModel.onAction(TreasureAction.GoOffline) }
                        } else null,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                LoadingState.Finished -> {
                    TreasureScreen(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
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

    val pagerState = rememberPagerState { state.factions.size }
    LaunchedEffect(state.selectedFactionPage) {
        pagerState.animateScrollToPage(state.selectedFactionPage)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        onAction(SelectFactionPage(pagerState.targetPage))
    }

    Column(
        modifier = modifier
    ) {
        if (state.factions.isNotEmpty()) {
            PrimaryScrollableTabRow(
                selectedTabIndex = state.selectedFactionPage,
                edgePadding = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                state.factions.forEachIndexed { index, faction ->
                    val selected = index == state.selectedFactionPage
                    FactionTab(
                        icon = faction.icon,
                        label = faction.name,
                        selected = selected,
                        onSelect = { onAction(SelectFactionPage(index)) },
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = MaterialTheme.spacing.medium,
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
            beyondViewportPageCount = state.factions.size,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge),
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            ) {
                val faction = state.factions[pageIndex]
                faction.categories.forEach { category ->
                    val selectedSubcategory = remember(state.selectedSubcategories[faction.id]) {
                        state.selectedSubcategories[faction.id]?.get(category.id) ?: 0
                    }

                    CategoryLayout(
                        category = category,
                        treasureAmounts = state.treasureAmounts,
                        selectedSubcategory = selectedSubcategory,
                        onClickSubcategory = {
                            onAction(SelectSubcategory(faction.id, category.id, it))
                        },
                        onChangeAmount = { treasureId: Int, amount: Int ->
                            onAction(ChangeTreasureAmount(treasureId, amount))
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