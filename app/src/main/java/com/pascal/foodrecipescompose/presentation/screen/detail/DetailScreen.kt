package com.pascal.foodrecipescompose.presentation.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.outlined.Favorite
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.IconCircleBorder
import com.pascal.foodrecipescompose.presentation.component.ImageModel
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.screen.detail.tabs.ContentDetailWithTabs
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState
import com.pascal.foodrecipescompose.utils.intentActionView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: DetailViewModel = hiltViewModel(),
    query: String,
    onNavBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isContentVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.detailRecipes.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadDetailRecipes(query)
        delay(200)
        isContentVisible = true
    }

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
                DetailContent(
                    isContentVisible = isContentVisible,
                    item = data,
                    viewModel = viewModel,
                    onNavBack = {
                        coroutineScope.launch {
                            isContentVisible = false
                            delay(500)
                            onNavBack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = false,
    item: DetailRecipesMapping?,
    viewModel: DetailViewModel?,
    onNavBack: () -> Unit
) {
    var favBtnClicked by rememberSaveable {
        mutableStateOf(item?.isFavorite ?: false)
    }

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
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
            ) {
                IconCircleBorder(
                    size = 42.dp,
                    padding = 6.dp,
                    imageVector = Icons.Outlined.ArrowBack
                ) {
                    onNavBack()
                }
            }
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
            ) {
                IconCircleBorder(
                    size = 42.dp,
                    padding = 6.dp,
                    imageVector = if (favBtnClicked) Icons.Outlined.Favorite
                    else Icons.Outlined.FavoriteBorder
                ) {
                    favBtnClicked = !favBtnClicked

                    viewModel?.updateFavorite(
                        FavoritesEntity(
                            item?.idMeal?.toIntOrNull() ?: 0,
                            item?.strMeal,
                            item?.strMealThumb,
                            item?.strCategory,
                            item?.strTags,
                            item?.strYoutube
                        ),
                        favBtnClicked
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically()
        ) {
            ImageRecipes(item = item)
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            TitleDetail(item = item)
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            ContentDetail(item = item)
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically{ fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically { fullHeight -> fullHeight }
        ) {
            ContentDetailWithTabs(item = item)
        }
    }
}

@Composable
fun ImageRecipes(
    modifier: Modifier = Modifier,
    item: DetailRecipesMapping?
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
    item: DetailRecipesMapping?
) {
    val context = LocalContext.current
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
                    Text(
                        text = item?.strTags ?: stringResource(id = R.string.no_tags),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        IconCircleBorder(
            size = 42.dp,
            padding = 6.dp,
            imageVector = Icons.Outlined.PlayArrow
        ) {
            intentActionView(context, item?.strYoutube.toString())
        }
    }
}

@Composable
fun ContentDetail(
    modifier: Modifier = Modifier,
    item: DetailRecipesMapping?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            TextTitle(text = stringResource(R.string.id_recipe))
            TextContent(text = item?.idMeal ?: "-")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitle(text = stringResource(R.string.area))
            TextContent(text = item?.strArea ?: "-")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            TextTitle(text = stringResource(R.string.category))
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
        val detail = DetailRecipesMapping(
            strMeal = "Fried Rice",
            strTags = "recipes",
            strCategory = "category"
        )
        DetailContent(
            item = detail,
            viewModel = null,
            onNavBack = {

            }
        )
    }
}