package com.pascal.foodrecipescompose.domain.repository.local

import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    suspend fun getListFavorite(): Flow<List<FavoritesEntity>>
    suspend fun updateFavorite(item: FavoritesEntity, checkFav: Boolean)
    suspend fun getFavoriteStatus(Id: Int): Boolean
}