package com.pascal.foodrecipescompose.presentation.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryItem
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.IconCircleBorder
import com.pascal.foodrecipescompose.presentation.component.ImageModel
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: CategoryViewModel = hiltViewModel(),
    query: String,
    onDetailClick: (String) -> Unit,
    onNavBack: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.loadFilterCategory(query)
    }

    val uiState by viewModel.filterCategory.collectAsState()

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
                CategoryContent(
                    query = query,
                    listCategory = data?.meals,
                    onDetailClick = {
                        onDetailClick(it)
                    },
                    onNavBack = {
                        onNavBack()
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryContent(
    modifier: Modifier = Modifier,
    query: String,
    listCategory: List<FilterCategoryItem?>?,
    onDetailClick: (String) -> Unit,
    onNavBack: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconCircleBorder(
                size = 42.dp,
                padding = 6.dp,
                imageVector = Icons.Outlined.ArrowBack
            ) {
                onNavBack()
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
                text = query.ifEmpty { stringResource(id = R.string.food_recipes) },
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(130.dp),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            items(listCategory!!.size) { index ->
                listCategory[index]?.let { item ->
                    CategoryItemFilter(
                        item = item,
                        onDetailClick = {
                            onDetailClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItemFilter(
    modifier: Modifier = Modifier,
    item: FilterCategoryItem,
    onDetailClick: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onDetailClick(item.idMeal ?: "") }
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageModel(context = context, url = item.strMealThumb),
                contentDescription = item.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.strMeal ?: "-",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoryPreview() {
    FoodRecipesComposeTheme {
        val listCategory = listOf(
            FilterCategoryItem(strMeal = "name1"),
            FilterCategoryItem(strMeal = "name2"),
            FilterCategoryItem(strMeal = "name3"),
            FilterCategoryItem(strMeal = "name4")
        )
        CategoryContent(
            listCategory = listCategory,
            query = "Food",
            onDetailClick = {},
            onNavBack = {}
        )
    }
}