package com.pascal.foodrecipescompose.presentation.screen.main

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
import com.pascal.foodrecipescompose.domain.usecase.UpdateFavorites
import com.pascal.foodrecipescompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoryUC: GetCategoryUC,
    private val getFilterCategoryUC: GetFilterCategoryUC,
    private val getListRecipesUC: GetListRecipesUC,
    private val getSearchRecipesUC: GetSearchRecipesUC,
    private val getDetailRecipesUC: GetDetailRecipesUC,
    private val updateFavorites: UpdateFavorites
): ViewModel() {

    private val _filterCategory = MutableStateFlow<UiState<FilterCategoryResponse?>>(UiState.Loading)
    val filterCategory: StateFlow<UiState<FilterCategoryResponse?>> = _filterCategory

    private val _recipes = MutableStateFlow<UiState<ListRecipesResponse?>>(UiState.Loading)
    val recipes: StateFlow<UiState<ListRecipesResponse?>> = _recipes

    private val _detailRecipes = MutableStateFlow<UiState<DetailRecipesMapping?>>(UiState.Loading)
    val detailRecipes: StateFlow<UiState<DetailRecipesMapping?>> = _detailRecipes

    suspend fun loadCategory(): CategoryResponse {
        return getCategoryUC.execute()
    }

    suspend fun loadFilterCategory(query: String) {
        try {
            val result = getFilterCategoryUC.execute(GetFilterCategoryUC.Params(query))
            _filterCategory.value = UiState.Success(result)
        } catch (e: Exception) {
            _filterCategory.value = UiState.Error(e)
        }
    }

    suspend fun loadListRecipes(query: String) {
        try {
            val result = getListRecipesUC.execute(GetListRecipesUC.Params(query))
            _recipes.value = UiState.Success(result)
        } catch (e: Exception) {
            _recipes.value = UiState.Error(e)
        }
    }

    suspend fun loadSearchRecipes(query: String) {
        try {
            val result = getSearchRecipesUC.execute(GetSearchRecipesUC.Params(query))
            _recipes.value = UiState.Success(result)
        } catch (e: Exception) {
            _recipes.value = UiState.Error(e)
        }
    }

    suspend fun loadDetailRecipes(query: String) {
        try {
            val result = getDetailRecipesUC.execute(GetDetailRecipesUC.Params(query))
            _detailRecipes.value = UiState.Success(result)
        } catch (e: Exception) {
            _detailRecipes.value = UiState.Error(e)
        }
    }

    fun updateFavMovie(item: FavoritesEntity, favChecked: Boolean) {
        viewModelScope.launch {
            updateFavorites.execute(UpdateFavorites.Params(item, favChecked))
        }
    }
}