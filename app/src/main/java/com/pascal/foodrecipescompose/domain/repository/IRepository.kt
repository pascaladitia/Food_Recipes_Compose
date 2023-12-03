package com.pascal.foodrecipescompose.domain.repository

import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse

interface IRepository {
    suspend fun getCategory(): CategoryResponse
    suspend fun getFilterCategory(query: String): FilterCategoryResponse
    suspend fun getListRecipe(query: String): ListRecipesResponse
    suspend fun getSearchRecipe(query: String): ListRecipesResponse
    suspend fun getDetailRecipe(query: String): ListRecipesResponse

    suspend fun updateFavorite(item: FavoritesEntity, checkFav: Boolean)
    suspend fun getFavoriteStatus(Id: Int): Boolean
}