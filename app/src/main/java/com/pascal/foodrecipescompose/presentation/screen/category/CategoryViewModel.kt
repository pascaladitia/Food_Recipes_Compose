package com.pascal.foodrecipescompose.presentation.screen.category

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
class CategoryViewModel @Inject constructor(
    private val getFilterCategoryUC: GetFilterCategoryUC,
): ViewModel() {

    private val _filterCategory = MutableStateFlow<UiState<FilterCategoryResponse?>>(UiState.Loading)
    val filterCategory: StateFlow<UiState<FilterCategoryResponse?>> = _filterCategory

    suspend fun loadFilterCategory(query: String) {
        viewModelScope.launch {
            val result = getFilterCategoryUC.execute(GetFilterCategoryUC.Params(query))
            result.collect {
                if (it.meals.isNullOrEmpty()) {
                    _filterCategory.value = UiState.Empty
                } else {
                    _filterCategory.value = UiState.Success(it)
                }
            }
            result.catch {
                _filterCategory.value = UiState.Error(it.message.toString())
            }
        }
    }
}