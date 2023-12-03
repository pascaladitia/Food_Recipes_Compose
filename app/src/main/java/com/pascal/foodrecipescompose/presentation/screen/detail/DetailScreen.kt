package com.pascal.foodrecipescompose.presentation.screen.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pascal.foodrecipescompose.domain.model.DetailRecipesInfo
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.screen.main.MainViewModel
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MainViewModel = hiltViewModel(),
    query: String,
    onNavBack: () ->  Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadDetailRecipes(query)
    }

    val uiState by viewModel.detailRecipes.collectAsState()

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        when(uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).exception.message
                ErrorScreen(message = message.toString())
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                DetailContent(detailRecipesInfo = data)
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    detailRecipesInfo: DetailRecipesInfo?
) {

}

@Preview
@Composable
fun DetailPreview() {
    FoodRecipesComposeTheme {
        val detail = DetailRecipesInfo()
        DetailContent(detailRecipesInfo = detail)
    }
}