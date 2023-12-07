package com.pascal.foodrecipescompose.presentation.screen.detail.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.model.IngredientMapping
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme

@Composable
fun IngredientsTab(modifier: Modifier = Modifier, item: DetailRecipesMapping?) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
                mainAxisSize = SizeMode.Wrap,
        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
    ) {
        item?.listIngredient?.forEachIndexed { index, data ->
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp)
                    .width(164.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                ) {
                    Text(
                        text = data.strIngredient ?: "-",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                    ) {
                        Text(text = data.strMeasure ?: "-", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(top = 6.dp, end = 8.dp))
                        .border(1.dp, Color.Black, CircleShape)
                        .clip(CircleShape)
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index.plus(1)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientsTabPreview() {
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
        IngredientsTab(item = item)
    }
}