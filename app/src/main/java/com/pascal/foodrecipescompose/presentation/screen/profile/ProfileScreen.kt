package com.pascal.foodrecipescompose.presentation.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadProfile()
    }

    val uiState by viewModel.profile.collectAsState()

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                ErrorScreen(message = message) {}
            }
            is UiState.Empty -> {
                ErrorScreen(message = stringResource(R.string.empty)) {}
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                ProfileContent(
                    itemProfile = data
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity
) {

}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    FoodRecipesComposeTheme {
        val profile = ProfileEntity(id = 1, name = "name1")
        ProfileContent(
            itemProfile = profile
        )
    }
}