package com.pascal.foodrecipescompose.presentation.screen.detail.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.model.IngredientMapping
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme

@Composable
fun ContentDetailWithTabs(
    modifier: Modifier = Modifier,
    item: DetailRecipesMapping?,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, Color.Black, CircleShape)
                .clip(CircleShape),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabRow(
                /* add indicator
               indicator = { tabPositions ->
                   TabRowDefaults.Indicator(
                       modifier = Modifier
                           .tabIndicatorOffset(tabPositions[selectedTabIndex])
                           .padding(4.dp)
                           .background(MaterialTheme.colorScheme.primary)
                           .border(1.dp, Color.Black, CircleShape)
                           .clip(CircleShape)
                   )
               },
               */
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    modifier = Modifier
                        .background(if (selectedTabIndex == 0) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .padding(8.dp)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 0) Color.White else Color.Black
                    )
                }
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    modifier = Modifier
                        .background(if (selectedTabIndex == 1) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .padding(8.dp)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = stringResource(R.string.step_cooking),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 1) Color.White else Color.Black
                    )
                }
            }
        }

        when (selectedTabIndex) {
            0 -> IngredientsTab(item = item)
            1 -> StepCookingTab(item = item)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ContentTabsPreview() {
    FoodRecipesComposeTheme {

        val list = mutableListOf(
            IngredientMapping("test1", "Test2"),
            IngredientMapping("test1", "Test2")
        )
        val item = DetailRecipesMapping(
            strMeal = "Fried Rice",
            strTags = "recipes",
            strCategory = "category",
            listIngredient = list
        )
        ContentDetailWithTabs(item = item)
    }
}