package com.pascal.foodrecipescompose.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.remote.dtos.CategoriesItem
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.MealsItem
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.IconCircleBorder
import com.pascal.foodrecipescompose.presentation.component.ImageModel
import com.pascal.foodrecipescompose.presentation.component.Search
import com.pascal.foodrecipescompose.presentation.component.ShimmerAnimation
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState
import com.pascal.foodrecipescompose.utils.generateRandomChar
import com.pascal.foodrecipescompose.utils.intentActionView
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit,
    onDetailClick: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val category by produceState<CategoryResponse?>(initialValue = null) {
        value = viewModel.loadCategory()
    }
    LaunchedEffect(key1 = true) {
        viewModel.loadListRecipes(generateRandomChar().toString())
    }

    val uiState by viewModel.recipes.collectAsState(initial = UiState.Loading)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(paddingValues)
    ) {
        when (uiState) {
            is UiState.Loading -> {
                ShimmerAnimation()
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                ErrorScreen(message = message) {
                    coroutineScope.launch {
                        viewModel.loadListRecipes(generateRandomChar().toString())
                    }
                }
            }
            is UiState.Empty -> {
                HomeContent(
                    isEmpty = true,
                    listCategory = category?.categories,
                    listRecipe = emptyList(),
                    onCategoryClick = { query ->
                        onCategoryClick(query)
                    },
                    onDetailClick = {},
                    onSearch = { query ->
                        coroutineScope.launch {
                            viewModel.loadSearchRecipes(query)
                        }
                    },
                    onRetry = {
                        coroutineScope.launch {
                            viewModel.loadListRecipes(generateRandomChar().toString())
                        }
                    }
                )
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                HomeContent(
                    listCategory = category?.categories,
                    listRecipe = data?.meals,
                    onCategoryClick = { query ->
                        onCategoryClick(query)
                    },
                    onDetailClick = { query ->
                        onDetailClick(query)
                    },
                    onSearch = { query ->
                        coroutineScope.launch {
                            viewModel.loadSearchRecipes(query)
                        }
                    },
                    onRetry = {
                        coroutineScope.launch {
                            viewModel.loadListRecipes(generateRandomChar().toString())
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    isEmpty: Boolean = false,
    listCategory: List<CategoriesItem?>?,
    listRecipe: List<MealsItem?>?,
    onCategoryClick: (String) -> Unit,
    onDetailClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.cookie), style = MaterialTheme.typography.headlineLarge)
            IconCircleBorder(imageVector = Icons.Outlined.Notifications) {

            }
        }
        Search { query ->
            onSearch(query)
        }
        SectionText(text = stringResource(R.string.category))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) {
            items(listCategory ?: emptyList()) { category ->
                category?.let {
                    CategoryItem(
                        item = it,
                        onCategoryClick = { query ->
                            onCategoryClick(query)
                        }
                    )
                }
            }
        }
        SectionText(text = stringResource(R.string.just_for_you))
        when(isEmpty) {
            true -> {
                ErrorScreen(message = stringResource(R.string.empty)) {
                    onRetry()
                }
            }
            false -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                ) {
                    items(listRecipe ?: emptyList()) { category ->
                        category?.let { item ->
                            RecipesItem(
                                item = item,
                                onDetailClick = { onDetailClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    item: CategoriesItem,
    onCategoryClick: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(80.dp)
            .clickable {
                onCategoryClick(item.strCategory ?: "")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageModel(context = context, url = item.strCategoryThumb),
                contentDescription = item.strCategory,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = item.strCategory ?: stringResource(id = R.string.category),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RecipesItem(
    modifier: Modifier = Modifier,
    item: MealsItem,
    onDetailClick: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(240.dp)
            .clickable { onDetailClick(item.idMeal ?: "") }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = ImageModel(context = context, url = item.strMealThumb),
                contentDescription = item.strCategory,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.strMeal ?: stringResource(R.string.food_recipes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.strTags ?: stringResource(R.string.no_tags),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.EmojiFoodBeverage, contentDescription = "")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = item.strCategory ?: "-", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconCircleBorder(imageVector = Icons.Outlined.PlayArrow) {
                    intentActionView(context, item.strYoutube.toString())
                }
            }
        }
    }
}

@Composable
fun SectionText(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(imageVector = Icons.Outlined.NavigateNext, contentDescription = "")
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    FoodRecipesComposeTheme {
        val listCategory = listOf(
            CategoriesItem(strCategory = "name1"),
            CategoriesItem(strCategory = "name2"),
            CategoriesItem(strCategory = "name3"),
            CategoriesItem(strCategory = "name4")
        )
        val listRecipe = listOf(
            MealsItem(strCategory = "name1"),
            MealsItem(strCategory = "name2"),
            MealsItem(strCategory = "name3"),
            MealsItem(strCategory = "name4")
        )
        HomeContent(
            listCategory = listCategory,
            listRecipe = listRecipe,
            onCategoryClick = {},
            onDetailClick = {},
            onSearch = {},
            onRetry = {}
        )
    }
}