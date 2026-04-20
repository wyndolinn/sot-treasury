package com.wynndie.sottreasurecalculator.sharedCore.navHosts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.wynndie.sottreasurecalculator.navigation.Route
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.events.EventsScreenRoot
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.events.EventsViewModel
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureScreenRoot
import com.wynndie.sottreasurecalculator.sharedFeatures.calculator.presentation.screens.treasure.TreasureViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun MainNavHost(
    rootBackStack: NavBackStack<NavKey>,
    startDestination: Route,
    modifier: Modifier = Modifier
) {

    val mainBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MainNavGraph.Treasure::class, Route.MainNavGraph.Treasure.serializer())
                    subclass(Route.MainNavGraph.Events::class, Route.MainNavGraph.Events.serializer())
                }
            }
        },
        startDestination
    )

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = mainBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Route.MainNavGraph.Treasure> {
                    val viewModel = koinViewModel<TreasureViewModel>()

                    TreasureScreenRoot(viewModel = viewModel)
                }
                entry<Route.MainNavGraph.Events> {
                    val viewModel = koinViewModel<EventsViewModel>()

                    EventsScreenRoot(viewModel = viewModel)
                }
            }
        )
    }
}