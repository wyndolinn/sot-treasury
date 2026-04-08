package com.wynndie.sottreasurecalculator.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {

    @Serializable
    data object MainNavGraph : Route, NavKey {

        @Serializable
        data object Treasure : Route, NavKey

        @Serializable
        data object Events : Route, NavKey
    }
}