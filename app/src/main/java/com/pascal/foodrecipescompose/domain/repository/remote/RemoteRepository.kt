package com.pascal.foodrecipescompose.domain.repository.remote

import com.pascal.foodrecipescompose.data.remote.AppService
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RemoteRepository @Inject constructor(private val appService: AppService):
    IRemoteRepository {
    override suspend fun getCategory(): CategoryResponse {
        return appService.getCategory().body()!!
    }

    override suspend fun getFilterCategory(query: String): Flow<FilterCategoryResponse> {
        return flowOf(appService.getFilterCategory(query).body()!!)
    }

    override suspend fun getListRecipe(query: String): Flow<ListRecipesResponse> {
        return flowOf(appService.getListRecipe(query).body()!!)
    }

    override suspend fun getSearchRecipe(query: String): Flow<ListRecipesResponse> {
        return flowOf(appService.getSearchRecipe(query).body()!!)
    }

    override suspend fun getDetailRecipe(query: String): ListRecipesResponse {
        return appService.getDetailRecipe(query).body()!!
    }
}