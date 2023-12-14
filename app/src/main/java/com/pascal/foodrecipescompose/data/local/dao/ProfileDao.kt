package com.pascal.foodrecipescompose.data.local.dao

import androidx.room.*
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity

@Dao
abstract class ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProfile(item: ProfileEntity) : Long

    @Delete
    abstract suspend fun deleteProfile(item: ProfileEntity) : Int

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_PROFILE}")
    abstract suspend fun getProfile(): ProfileEntity

    @Query("DELETE FROM ${DatabaseConstants.TABLE_PROFILE}")
    abstract suspend fun clearProfilesTable()
}