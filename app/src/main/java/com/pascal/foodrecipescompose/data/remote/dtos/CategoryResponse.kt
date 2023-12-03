package com.pascal.foodrecipescompose.data.remote.dtos

data class CategoryResponse(
	val categories: List<CategoriesItem?>? = null
)

data class CategoriesItem(
	val strCategory: String? = null,
	val strCategoryDescription: String? = null,
	val idCategory: String? = null,
	val strCategoryThumb: String? = null
)
