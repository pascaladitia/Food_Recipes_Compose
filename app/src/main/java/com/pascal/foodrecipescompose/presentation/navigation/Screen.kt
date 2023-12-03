package com.pascal.foodrecipescompose.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object CategoryScreen: Screen("category/{query}") {
        fun createRoute(query: String) = "category/$query"
    }
    object FavoriteScreen: Screen("favorite")
    object DetailScreen: Screen("detail/{query}") {
        fun createRoute(query: String) = "detail/$query"
    }
}