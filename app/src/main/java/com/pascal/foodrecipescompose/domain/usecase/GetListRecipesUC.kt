package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import com.pascal.foodrecipescompose.domain.repository.remote.IRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListRecipesUC @Inject constructor(private val repository: IRemoteRepository) {
    suspend fun execute(params: Params): Flow<ListRecipesResponse> {
        return repository.getListRecipe(params.query)
    }

    class Params(val query: String)
}