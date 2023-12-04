package com.pascal.foodrecipescompose.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.domain.model.DetailRecipesInfo
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.IconCircleBorder
import com.pascal.foodrecipescompose.presentation.component.ImageModel
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
                DetailContent(
                    detailRecipesInfo = data,
                    onNavBack = {
                        onNavBack()
                    }
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    detailRecipesInfo: DetailRecipesInfo?,
    onNavBack: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
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
            IconCircleBorder(
                size = 42.dp,
                padding = 6.dp,
                imageVector = Icons.Outlined.FavoriteBorder
            ) {

            }
        }
        ImageRecipes(item = detailRecipesInfo)
        TitleDetail(item = detailRecipesInfo) {

        }
        ContentDetail(item = detailRecipesInfo)
    }
}

@Composable
fun ImageRecipes(
    modifier: Modifier = Modifier,
    item: DetailRecipesInfo?
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageModel(context = context, url = item?.strMealThumb),
        contentDescription = item?.strMeal,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
    )
}

@Composable
fun TitleDetail(
    modifier: Modifier = Modifier,
    item: DetailRecipesInfo?,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item?.strMeal ?: stringResource(id = R.string.food_recipes),
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                    Text(text = item?.strTags ?: "-", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        IconCircleBorder(
            size = 42.dp,
            padding = 6.dp,
            imageVector = Icons.Outlined.PlayArrow
        ) {
            onClick()
        }
    }
}

@Composable
fun ContentDetail(
    modifier: Modifier = Modifier,
    item: DetailRecipesInfo?,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            TextTitle(text = "Id Recipe")
            TextContent(text = item?.idMeal ?: "-")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitle(text = "Area")
            TextContent(text = item?.strArea ?: "-")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            TextTitle(text = "Category")
            TextContent(text = item?.strCategory ?: "-")
        }
    }
}

@Composable
fun TextTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun TextContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPreview() {
    FoodRecipesComposeTheme {
        val detail = DetailRecipesInfo(
            strMeal = "Fried Rice",
            strTags = "recipes",
            strCategory = "category"
        )
        DetailContent(
            detailRecipesInfo = detail,
            onNavBack = {

            }
        )
    }
}