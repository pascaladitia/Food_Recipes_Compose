package com.pascal.foodrecipescompose.data.remote.dtos

data class FilterCategoryResponse(
	val meals: List<FilterCategoryItem?>? = null
)

data class FilterCategoryItem(
	val strMealThumb: String? = null,
	val idMeal: String? = null,
	val strMeal: String? = null
)

