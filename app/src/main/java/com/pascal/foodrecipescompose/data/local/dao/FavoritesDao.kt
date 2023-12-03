package com.pascal.foodrecipescompose.data.local.dao

import androidx.room.*
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity

@Dao
abstract class FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFavorite(cachedTest: FavoritesEntity) : Long

    @Delete
    abstract suspend fun deleteFavorite(item: FavoritesEntity) : Int

    @Query("SELECT count(*) FROM " + DatabaseConstants.TABLE_FAVORITES)
    abstract suspend fun countTests(): Int

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_FAVORITES}")
    abstract suspend fun getFavoriteList(): List<FavoritesEntity>?

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_FAVORITES} where id=:id")
    abstract suspend fun getFavorite(id:Int): FavoritesEntity?

    @Query("DELETE FROM ${DatabaseConstants.TABLE_FAVORITES}")
    abstract suspend fun clearFavoritesTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllFavorites(cachedTests: List<FavoritesEntity>) : List<Long>

}