package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import com.pascal.foodrecipescompose.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListRecipesUC @Inject constructor(private val repository: IRepository) {
    suspend fun execute(params: Params): Flow<ListRecipesResponse> {
        return repository.getListRecipe(params.query)
    }

    class Params(val query: String)
}