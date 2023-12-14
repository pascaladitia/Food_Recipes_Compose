package com.pascal.foodrecipescompose.data.local

import com.pascal.foodrecipescompose.data.local.database.AppDatabase
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.di.AppMainDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(@AppMainDB private val database: AppDatabase){

    // Favorite
    suspend fun storeFavoriteItem(favoritesEntity: FavoritesEntity) {
        database.favoritesDao().insertFavorite(favoritesEntity)
    }

    suspend fun deleteFavoriteItem(favoritesEntity: FavoritesEntity) {
        database.favoritesDao().deleteFavorite(favoritesEntity)
    }

    suspend fun getFavorites(): List<FavoritesEntity>? {
        return database.favoritesDao().getFavoriteList()
    }

    suspend fun getFavorite(id: Int): Boolean {
        return (database.favoritesDao().getFavorite(id) != null)
    }

    // Profile
    suspend fun storeProfileItem(profileEntity: ProfileEntity) {
        database.profileDao().insertProfile(profileEntity)
    }

    suspend fun deleteProfileItem(profileEntity: ProfileEntity) {
        database.profileDao().deleteProfile(profileEntity)
    }

    suspend fun getProfile(): ProfileEntity? {
        return database.profileDao().getProfile()
    }
}