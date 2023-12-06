package com.pascal.foodrecipescompose.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.usecase.GetDetailRecipesUC
import com.pascal.foodrecipescompose.domain.usecase.FavoriteUC
import com.pascal.foodrecipescompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailRecipesUC: GetDetailRecipesUC,
    private val favoriteUC: FavoriteUC
): ViewModel() {

    private val _detailRecipes = MutableStateFlow<UiState<DetailRecipesMapping?>>(UiState.Loading)
    val detailRecipes: StateFlow<UiState<DetailRecipesMapping?>> = _detailRecipes

    suspend fun loadDetailRecipes(query: String) {
        try {
            val result = getDetailRecipesUC.execute(GetDetailRecipesUC.Params(query))
            _detailRecipes.value = UiState.Success(result)
        } catch (e: Exception) {
            _detailRecipes.value = UiState.Error(e.toString())
        }
    }

    fun updateFavorite(item: FavoritesEntity, favChecked: Boolean) {
        viewModelScope.launch {
            favoriteUC.updateFavorite(FavoriteUC.Params(item, favChecked))
        }
    }
}