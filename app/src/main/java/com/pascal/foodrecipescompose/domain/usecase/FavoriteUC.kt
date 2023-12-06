package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteUC @Inject constructor(private val repository: IRepository) {
    suspend fun getListFavorite(): Flow<List<FavoritesEntity>> {
        return repository.getListFavorite()
    }

    suspend fun updateFavorite(params: Params) {
        repository.updateFavorite(params.item, params.checked)
    }
    class Params(val item: FavoritesEntity, val checked: Boolean)
}