package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.domain.model.DetailRecipesInfo
import com.pascal.foodrecipescompose.domain.repository.IRepository
import javax.inject.Inject

class GetDetailRecipesUC @Inject constructor(private val repository: IRepository) {
    suspend fun execute(params: Params): DetailRecipesInfo {
        val recipe = repository.getDetailRecipe(params.query).meals?.firstOrNull()

        val listIngredient = mutableListOf<String?>()
        listIngredient.add(recipe?.strIngredient1)
        listIngredient.add(recipe?.strIngredient2)
        listIngredient.add(recipe?.strIngredient3)
        listIngredient.add(recipe?.strIngredient4)
        listIngredient.add(recipe?.strIngredient5)
        listIngredient.add(recipe?.strIngredient6)
        listIngredient.add(recipe?.strIngredient7)
        listIngredient.add(recipe?.strIngredient8)
        listIngredient.add(recipe?.strIngredient9)
        listIngredient.add(recipe?.strIngredient10)
        listIngredient.add(recipe?.strIngredient11)
        listIngredient.add(recipe?.strIngredient12)
        listIngredient.add(recipe?.strIngredient13)
        listIngredient.add(recipe?.strIngredient14)
        listIngredient.add(recipe?.strIngredient15)
        listIngredient.add(recipe?.strIngredient16)
        listIngredient.add(recipe?.strIngredient17)
        listIngredient.add(recipe?.strIngredient18)
        listIngredient.add(recipe?.strIngredient19)
        listIngredient.add(recipe?.strIngredient20)

        val listMeasure = mutableListOf<String?>()
        listMeasure.add(recipe?.strMeasure1)
        listMeasure.add(recipe?.strMeasure2)
        listMeasure.add(recipe?.strMeasure3)
        listMeasure.add(recipe?.strMeasure4)
        listMeasure.add(recipe?.strMeasure5)
        listMeasure.add(recipe?.strMeasure6)
        listMeasure.add(recipe?.strMeasure7)
        listMeasure.add(recipe?.strMeasure8)
        listMeasure.add(recipe?.strMeasure9)
        listMeasure.add(recipe?.strMeasure10)
        listMeasure.add(recipe?.strMeasure1)
        listMeasure.add(recipe?.strMeasure12)
        listMeasure.add(recipe?.strMeasure13)
        listMeasure.add(recipe?.strMeasure14)
        listMeasure.add(recipe?.strMeasure15)
        listMeasure.add(recipe?.strMeasure16)
        listMeasure.add(recipe?.strMeasure17)
        listMeasure.add(recipe?.strMeasure18)
        listMeasure.add(recipe?.strMeasure19)
        listMeasure.add(recipe?.strMeasure20)

        return DetailRecipesInfo(
            strImageSource = recipe?.strImageSource,
            strCategory = recipe?.strCategory,
            strArea = recipe?.strArea,
            strCreativeCommonsConfirmed = recipe?.strCreativeCommonsConfirmed,
            strTags = recipe?.strTags,
            idMeal = recipe?.idMeal,
            strInstructions = recipe?.strInstructions,
            strMealThumb = recipe?.strMealThumb,
            strYoutube = recipe?.strYoutube,
            strMeal = recipe?.strMeal,
            dateModified = recipe?.dateModified,
            strDrinkAlternate = recipe?.strDrinkAlternate,
            strSource = recipe?.strSource,
            strIngredient = listIngredient,
            strMeasure = listMeasure
        )
    }

    class Params(val query: String)
}