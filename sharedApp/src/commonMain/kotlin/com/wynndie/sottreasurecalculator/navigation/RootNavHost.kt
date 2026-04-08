package com.wynndie.sottreasurecalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.wynndie.sottreasurecalculator.sharedCore.navHosts.MainNavHost
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalSerializationApi::class, KoinExperimentalAPI::class)
@Composable
fun RootNavHost(
    startDestination: Route,
    modifier: Modifier = Modifier
) {

    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MainNavGraph::class, Route.MainNavGraph.serializer())
                }
            }
        },
        startDestination
    )

    NavDisplay(
        modifier = modifier,
        backStack = rootBackStack,
        onBack = { rootBackStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.MainNavGraph> {
                MainNavHost(
                    rootBackStack = rootBackStack,
                    startDestination = Route.MainNavGraph.Treasure
                )
            }
        }
    )
}