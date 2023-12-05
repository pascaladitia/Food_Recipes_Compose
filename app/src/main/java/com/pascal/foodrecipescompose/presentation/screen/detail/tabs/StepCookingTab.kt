package com.pascal.foodrecipescompose.presentation.screen.detail.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.model.IngredientMapping
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme

@Composable
fun StepCookingTab(item: DetailRecipesMapping?) {
    val recipeString = item?.strInstructions?.trimIndent()

    val recipeList = recipeString?.split("\r\n")
    for (step in recipeList ?: emptyList()) {
        Text(step)
    }
}

@Preview(showBackground = true)
@Composable
fun StepCookingTabPreview() {
    FoodRecipesComposeTheme {

        val list = mutableListOf(
            IngredientMapping("test1", "Test2"),
            IngredientMapping("test1", "Test2")
        )
        val item = DetailRecipesMapping(
            strMeal = "Fried Rice",
            strTags = "recipes",
            strCategory = "category",
            listIngredient = list,
            strInstructions = """
                Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.\r\n
                Combine soy sauce, ½ cup water, brown sugar, ginger and garlic in a small saucepan and cover. Bring to a boil over medium heat. Remove lid and cook for one minute once boiling.\r\n
                Meanwhile, stir together the corn starch and 2 tablespoons of water in a separate dish until smooth. Once sauce is boiling, add mixture to the saucepan and stir to combine. Cook until the sauce starts to thicken then remove from heat.\r\n
                Place the chicken breasts in the prepared pan. Pour one cup of the sauce over top of chicken. Place chicken in oven and bake 35 minutes or until cooked through. Remove from oven and shred chicken in the dish using two forks.\r\n
                *Meanwhile, steam or cook the vegetables according to package directions.\r\nAdd the cooked vegetables and rice to the casserole dish with the chicken. Add most of the remaining sauce, reserving a bit to drizzle over the top when serving. Gently toss everything together in the casserole dish until combined. Return to oven and cook 15 minutes. Remove from oven and let stand 5 minutes before serving. Drizzle each serving with remaining sauce. Enjoy!
            """.trimIndent()
        )
        StepCookingTab(item = item)
    }
}