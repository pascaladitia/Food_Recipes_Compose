package com.pascal.foodrecipescompose.domain.usecase

import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.domain.repository.local.ILocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUC @Inject constructor(private val repository: ILocalRepository) {
    suspend fun getProfile(): Flow<ProfileEntity> {
        return repository.getProfile()
    }

    suspend fun addProfile(params: Params) {
        repository.addProfile(params.item)
    }

    suspend fun deleteProfile(params: Params) {
        repository.deleteProfile(params.item)
    }

    class Params(val item: ProfileEntity)
}