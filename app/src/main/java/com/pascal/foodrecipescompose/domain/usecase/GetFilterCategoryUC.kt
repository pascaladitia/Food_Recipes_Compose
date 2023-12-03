package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.domain.repository.IRepository
import javax.inject.Inject

class GetFilterCategoryUC @Inject constructor(private val repository: IRepository) {
    suspend fun execute(params: Params): FilterCategoryResponse {
        return repository.getFilterCategory(params.query)
    }

    class Params(val query: String)
}