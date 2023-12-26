package com.pascal.foodrecipescompose.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pascal.foodrecipescompose.presentation.component.BottomBar
import com.pascal.foodrecipescompose.presentation.screen.category.CategoryScreen
import com.pascal.foodrecipescompose.presentation.screen.detail.DetailScreen
import com.pascal.foodrecipescompose.presentation.screen.favorite.FavoriteScreen
import com.pascal.foodrecipescompose.presentation.screen.home.HomeScreen
import com.pascal.foodrecipescompose.presentation.screen.profile.MapsScreen
import com.pascal.foodrecipescompose.presentation.screen.profile.ProfileScreen
import com.pascal.foodrecipescompose.presentation.screen.splash.SplashScreen
import com.pascal.foodrecipescompose.utils.QUERY

@Composable
fun RouteScreen(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(Screen.HomeScreen.route, Screen.FavoriteScreen.route, Screen.ProfileScreen.route)) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    paddingValues = paddingValues,
                    onDetailClick = {
                        navController.navigate(Screen.DetailScreen.createRoute(it))
                    },
                    onCategoryClick = {
                        navController.navigate(Screen.CategoryScreen.createRoute(it))
                    }
                )
            }
            composable(route = Screen.CategoryScreen.route) {
                CategoryScreen(
                    paddingValues = paddingValues,
                    query = it.arguments?.getString(QUERY) ?: "",
                    onDetailClick = { query ->
                        navController.navigate(Screen.DetailScreen.createRoute(query))
                    },
                    onNavBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = Screen.FavoriteScreen.route) {
                FavoriteScreen(
                    paddingValues = paddingValues,
                    onDetailClick = { query ->
                        navController.navigate(Screen.DetailScreen.createRoute(query))
                    }
                )
            }
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen(
                    paddingValues = paddingValues,
                    onMaps = {
                        navController.navigate(Screen.MapsScreen.route)
                    }
                )
            }
            composable(route = Screen.MapsScreen.route) {
                MapsScreen(
                    paddingValues = paddingValues
                )
            }
            composable(
                route = Screen.DetailScreen.route,
                arguments = listOf(
                    navArgument(QUERY) {
                        type = NavType.StringType
                        nullable = false
                    },
                )
            ) {
                DetailScreen(
                    paddingValues = paddingValues,
                    query = it.arguments?.getString(QUERY) ?: "",
                    onNavBack = {
                        navController.popBackStack()
                    })
            }
        }
    }
}
