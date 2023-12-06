package com.pascal.foodrecipescompose.presentation.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import com.pascal.foodrecipescompose.domain.model.DetailRecipesMapping
import com.pascal.foodrecipescompose.domain.usecase.GetCategoryUC
import com.pascal.foodrecipescompose.domain.usecase.GetDetailRecipesUC
import com.pascal.foodrecipescompose.domain.usecase.GetFilterCategoryUC
import com.pascal.foodrecipescompose.domain.usecase.GetListRecipesUC
import com.pascal.foodrecipescompose.domain.usecase.GetSearchRecipesUC
import com.pascal.foodrecipescompose.domain.usecase.FavoriteUC
import com.pascal.foodrecipescompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUC: FavoriteUC
): ViewModel() {

    private val _favorite = MutableStateFlow<UiState<List<FavoritesEntity>?>>(UiState.Loading)
    val favorite: StateFlow<UiState<List<FavoritesEntity>?>> = _favorite

    suspend fun loadListFavorite() {
        viewModelScope.launch {
            _favorite.value = UiState.Loading
            val result = favoriteUC.getListFavorite()
            result.collect {
                if (it.isEmpty()) {
                    _favorite.value = UiState.Empty
                } else {
                    _favorite.value = UiState.Success(it)
                }
            }

            result.catch {
                _favorite.value = UiState.Error(it.message.toString())
            }
        }
    }
}