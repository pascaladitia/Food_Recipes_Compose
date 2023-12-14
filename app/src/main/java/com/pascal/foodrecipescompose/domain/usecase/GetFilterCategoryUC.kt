package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.domain.repository.remote.IRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilterCategoryUC @Inject constructor(private val repository: IRemoteRepository) {
    suspend fun execute(params: Params): Flow<FilterCategoryResponse> {
        return repository.getFilterCategory(params.query)
    }

    class Params(val query: String)
}