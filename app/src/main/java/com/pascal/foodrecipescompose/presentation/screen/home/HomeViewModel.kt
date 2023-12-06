package com.pascal.foodrecipescompose.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import com.pascal.foodrecipescompose.domain.usecase.GetCategoryUC
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
class HomeViewModel @Inject constructor(
    private val getCategoryUC: GetCategoryUC,
    private val getListRecipesUC: GetListRecipesUC,
    private val getSearchRecipesUC: GetSearchRecipesUC,
    private val favoriteUC: FavoriteUC
): ViewModel() {

    private val _recipes = MutableStateFlow<UiState<ListRecipesResponse?>>(UiState.Loading)
    val recipes: StateFlow<UiState<ListRecipesResponse?>> = _recipes

    suspend fun loadCategory(): CategoryResponse {
        return getCategoryUC.execute()
    }

    suspend fun loadListRecipes(query: String) {
        viewModelScope.launch {
            _recipes.value = UiState.Loading
            val result = getListRecipesUC.execute(GetListRecipesUC.Params(query))
            result.collect {
                if (it.meals.isNullOrEmpty()) {
                    _recipes.value = UiState.Empty
                } else {
                    _recipes.value = UiState.Success(it)
                }
            }

            result.catch {
                _recipes.value = UiState.Error(it.message.toString())
            }
        }
    }

    suspend fun loadSearchRecipes(query: String) {
        viewModelScope.launch {
            _recipes.value = UiState.Loading
            val result = getSearchRecipesUC.execute(GetSearchRecipesUC.Params(query))
            result.collect {
                if (it.meals.isNullOrEmpty()) {
                    _recipes.value = UiState.Empty
                } else {
                    _recipes.value = UiState.Success(it)
                }
            }

            result.catch {
                _recipes.value = UiState.Error(it.message.toString())
            }
        }
    }

    fun updateFavMovie(item: FavoritesEntity, favChecked: Boolean) {
        viewModelScope.launch {
            favoriteUC.updateFavorite(FavoriteUC.Params(item, favChecked))
        }
    }
}