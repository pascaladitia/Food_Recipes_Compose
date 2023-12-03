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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.remote.dtos.CategoriesItem
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.MealsItem
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.component.Search
import com.pascal.foodrecipescompose.presentation.screen.main.MainViewModel
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState
import com.pascal.foodrecipescompose.utils.generateRandomChar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MainViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit,
    onDetailClick: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadListRecipes(generateRandomChar().toString())
    }

    val category by produceState<CategoryResponse?>(initialValue = null) {
        value = viewModel.loadCategory()
    }

    val uiState by viewModel.recipes.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(paddingValues)
    ) {
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).exception.message
                ErrorScreen(message = message.toString())
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                HomeContent(
                    listCategory = category?.categories,
                    listRecipe = data?.meals,
                    onCategoryClick = { query ->
                        onCategoryClick(query)
                    }
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    listCategory: List<CategoriesItem?>?,
    listRecipe: List<MealsItem?>?,
    onCategoryClick: (String) -> Unit,
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
            Text(text = "Cookie", style = MaterialTheme.typography.headlineLarge)
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
        }
        Search()
        SectionText(text = "Category")
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
        SectionText(text = "Just for you")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) {
            items(listRecipe ?: emptyList()) { category ->
                category?.let { RecipesItem(item = it) }
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
    val model = remember {
        ImageRequest.Builder(context)
            .data(item.strCategoryThumb)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .error(R.drawable.logo)
            .build()
    }

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
                model = model,
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
                text = item.strCategory ?: "category",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RecipesItem(modifier: Modifier = Modifier, item: MealsItem) {
    val context = LocalContext.current
    val model = remember {
        ImageRequest.Builder(context)
            .data(item.strMealThumb)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .error(R.drawable.no_image)
            .build()
    }

    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(240.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = model,
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
                text = item.strMeal ?: "Food Recipes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.strTags.toString(), style = MaterialTheme.typography.bodySmall)
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
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Black, CircleShape)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(4.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = "")
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
        HomeContent(
            listCategory = listCategory,
            listRecipe = emptyList(),
            onCategoryClick = {}
        )
    }
}