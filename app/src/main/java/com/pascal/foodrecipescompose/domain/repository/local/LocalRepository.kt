package com.pascal.foodrecipescompose.domain.repository.local

import com.pascal.foodrecipescompose.data.local.LocalDataSource
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocalRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): ILocalRepository {

    //Favorite
    override suspend fun getListFavorite(): Flow<List<FavoritesEntity>> {
        return flowOf(localDataSource.getFavorites()!!)
    }

    override suspend fun updateFavorite(item: FavoritesEntity, checkFav: Boolean) {
        if (checkFav) {
            localDataSource.storeFavoriteItem(item)
        } else {
            localDataSource.deleteFavoriteItem(item)
        }
    }
    override suspend fun getFavoriteStatus(Id: Int): Boolean {
        return localDataSource.getFavorite(Id)
    }

    // Profile
    override suspend fun getProfile(): Flow<ProfileEntity> {
        return flowOf(localDataSource.getProfile())
    }

    override suspend fun addProfile(item: ProfileEntity) {
        return localDataSource.storeProfileItem(item)
    }

    override suspend fun deleteProfile(item: ProfileEntity) {
        return localDataSource.deleteProfileItem(item)
    }
}