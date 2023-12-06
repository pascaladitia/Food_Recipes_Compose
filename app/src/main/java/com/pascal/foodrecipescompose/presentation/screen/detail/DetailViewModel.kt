package com.pascal.foodrecipescompose.presentation.screen.detail

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailRecipesUC: GetDetailRecipesUC,
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
}