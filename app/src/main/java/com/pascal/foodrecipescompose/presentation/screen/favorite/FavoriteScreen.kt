package com.pascal.foodrecipescompose.presentation.screen.favorite

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.IconCircleBorder
import com.pascal.foodrecipescompose.presentation.component.ImageModel
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState
import com.pascal.foodrecipescompose.utils.intentActionView

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onDetailClick: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadListFavorite()
    }

    val uiState by viewModel.favorite.collectAsState()

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
                ErrorScreen(message = stringResource(R.string.favorite_empty)) {}
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                FavoriteContent(
                    listFavorite = data,
                    onDetailClick = {
                        onDetailClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    listFavorite: List<FavoritesEntity?>?,
    onDetailClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        state = rememberLazyGridState(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(24.dp)
    ) {
        items(listFavorite!!.size) { index ->
            listFavorite[index]?.let { item ->
                FavoriteItem(
                    item = item,
                    onDetailClick = {
                        onDetailClick(it.toString())
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    item: FavoritesEntity?,
    onDetailClick: (Int) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(150.dp)
            .clickable { onDetailClick(item?.id ?: 0) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = ImageModel(context = context, url = item?.imagePath),
                contentDescription = item?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item?.name ?: stringResource(R.string.food_recipes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item?.tags ?: stringResource(R.string.no_tags),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .widthIn(max = 90.dp)
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                ) {
                    Row(
                        modifier = Modifier.height(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item?.category ?: "-",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconCircleBorder(imageVector = Icons.Outlined.PlayArrow) {
                    intentActionView(context, item?.youtubeUrl.toString())
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FavoritePreview() {
    FoodRecipesComposeTheme {
        val list = listOf(
            FavoritesEntity(id = 1, name = "name1"),
            FavoritesEntity(id = 1, name = "name1"),
            FavoritesEntity(id = 1, name = "name1"),
            FavoritesEntity(id = 1, name = "name1"),
        )
        FavoriteContent(
            listFavorite = list,
            onDetailClick = {}
        )
    }
}