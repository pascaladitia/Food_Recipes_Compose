package com.pascal.foodrecipescompose.presentation.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.presentation.component.BottomBar
import com.pascal.foodrecipescompose.presentation.navigation.Screen
import com.pascal.foodrecipescompose.presentation.screen.category.CategoryScreen
import com.pascal.foodrecipescompose.presentation.screen.favorite.FavoriteScreen
import com.pascal.foodrecipescompose.presentation.screen.home.HomeScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen(onTimeout = {
                navigateToMainScreen()
            })
        }
    }

    private fun navigateToMainScreen() {
        setContent {
            FoodRecipesComposeTheme(darkTheme = false) {
                AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        onTimeout.invoke()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
        )
    }
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(Screen.HomeScreen.route, Screen.FavoriteScreen.route)) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
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
                    query = it.arguments?.getString("query") ?: "",
                    onDetailClick = {

                    }
                )
            }
            composable(route = Screen.FavoriteScreen.route) {
                FavoriteScreen(

                )
            }
            composable(
                route = Screen.DetailScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                        nullable = false
                    },
                )
            ) {
//                DetailScreen(
//                    movieId = it.arguments!!.getInt("id"),
//                    onNavBack = {
//                        navController.popBackStack()
//                    })
            }
        }
    }
}


