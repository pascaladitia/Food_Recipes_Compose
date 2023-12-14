package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.domain.repository.remote.IRemoteRepository
import javax.inject.Inject

class GetCategoryUC @Inject constructor(private val repository: IRemoteRepository) {
    suspend fun execute(): CategoryResponse {
        return repository.getCategory()
    }
}