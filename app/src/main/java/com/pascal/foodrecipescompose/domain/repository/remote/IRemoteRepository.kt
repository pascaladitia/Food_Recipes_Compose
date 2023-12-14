package com.pascal.foodrecipescompose.domain.repository.remote

import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteRepository {
    suspend fun getCategory(): CategoryResponse
    suspend fun getFilterCategory(query: String): Flow<FilterCategoryResponse>
    suspend fun getListRecipe(query: String): Flow<ListRecipesResponse>
    suspend fun getSearchRecipe(query: String): Flow<ListRecipesResponse>
    suspend fun getDetailRecipe(query: String): ListRecipesResponse
}