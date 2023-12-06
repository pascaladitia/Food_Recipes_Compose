package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.domain.repository.IRepository
import javax.inject.Inject

class FavoriteUC @Inject constructor(private val repository: IRepository) {
    suspend fun updateFavorite(params: Params) {
        repository.updateFavorite(params.item, params.checked)
    }
    class Params(val item: FavoritesEntity, val checked: Boolean)
}