package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.model.IngredientMapping
import com.pascal.foodrecipescompose.domain.repository.IRepository
import javax.inject.Inject

class GetDetailRecipesUC @Inject constructor(private val repository: IRepository) {
    suspend fun execute(params: Params): DetailRecipesMapping {
        val recipe = repository.getDetailRecipe(params.query).meals?.firstOrNull()

        val listIngredient = mutableListOf<IngredientMapping?>()
        listIngredient.add(IngredientMapping(recipe?.strIngredient1, recipe?.strMeasure1))
        listIngredient.add(IngredientMapping(recipe?.strIngredient2, recipe?.strMeasure2))
        listIngredient.add(IngredientMapping(recipe?.strIngredient3, recipe?.strMeasure3))
        listIngredient.add(IngredientMapping(recipe?.strIngredient4, recipe?.strMeasure4))
        listIngredient.add(IngredientMapping(recipe?.strIngredient5, recipe?.strMeasure5))
        listIngredient.add(IngredientMapping(recipe?.strIngredient6, recipe?.strMeasure6))
        listIngredient.add(IngredientMapping(recipe?.strIngredient7, recipe?.strMeasure7))
        listIngredient.add(IngredientMapping(recipe?.strIngredient8, recipe?.strMeasure8))
        listIngredient.add(IngredientMapping(recipe?.strIngredient9, recipe?.strMeasure9))
        listIngredient.add(IngredientMapping(recipe?.strIngredient10, recipe?.strMeasure10))
        listIngredient.add(IngredientMapping(recipe?.strIngredient11, recipe?.strMeasure11))
        listIngredient.add(IngredientMapping(recipe?.strIngredient12, recipe?.strMeasure12))
        listIngredient.add(IngredientMapping(recipe?.strIngredient13, recipe?.strMeasure13))
        listIngredient.add(IngredientMapping(recipe?.strIngredient14, recipe?.strMeasure14))
        listIngredient.add(IngredientMapping(recipe?.strIngredient15, recipe?.strMeasure15))
        listIngredient.add(IngredientMapping(recipe?.strIngredient16, recipe?.strMeasure16))
        listIngredient.add(IngredientMapping(recipe?.strIngredient17, recipe?.strMeasure17))
        listIngredient.add(IngredientMapping(recipe?.strIngredient18, recipe?.strMeasure18))
        listIngredient.add(IngredientMapping(recipe?.strIngredient19, recipe?.strMeasure19))
        listIngredient.add(IngredientMapping(recipe?.strIngredient20, recipe?.strMeasure20))

        return DetailRecipesMapping(
            strImageSource = recipe?.strImageSource,
            strCategory = recipe?.strCategory,
            strArea = recipe?.strArea,
            strCreativeCommonsConfirmed = recipe?.strCreativeCommonsConfirmed.toString(),
            strTags = recipe?.strTags,
            idMeal = recipe?.idMeal,
            strInstructions = recipe?.strInstructions,
            strMealThumb = recipe?.strMealThumb,
            strYoutube = recipe?.strYoutube,
            strMeal = recipe?.strMeal,
            dateModified = recipe?.dateModified.toString(),
            strDrinkAlternate = recipe?.strDrinkAlternate.toString(),
            strSource = recipe?.strSource,
            strIngredient = listIngredient
        )
    }

    class Params(val query: String)
}