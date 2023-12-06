package com.pascal.foodrecipescompose.domain.repository

import com.pascal.foodrecipescompose.data.local.LocalDataSource
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.remote.AppService
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class Repository @Inject constructor(private val appService: AppService, private val localDataSource: LocalDataSource): IRepository {
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

    override suspend fun getListFavorite(): Flow<List<FavoritesEntity>> {
        return flowOf(localDataSource.getFavorites()!!)
    }

    override suspend fun updateFavorite(item: FavoritesEntity, checkFav: Boolean) {
        if (checkFav) {
            localDataSource.storeFavoriteItem(item)
        } else {
            localDataSource.deleteFavoriteItem(item)
        }
    }
    override suspend fun getFavoriteStatus(Id: Int): Boolean {
        return localDataSource.getFavorite(Id)
    }
}