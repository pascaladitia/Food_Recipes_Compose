package com.pascal.foodrecipescompose.presentation.navigation

import com.pascal.foodrecipescompose.utils.QUERY

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash")
    object HomeScreen: Screen("home")
    object CategoryScreen: Screen("category/{$QUERY}") {
        fun createRoute(query: String) = "category/$query"
    }
    object FavoriteScreen: Screen("favorite")
    object ProfileScreen: Screen("profile")
    object MapsScreen: Screen("maps")
    object DetailScreen: Screen("detail/{$QUERY}") {
        fun createRoute(query: String) = "detail/$query"
    }
}