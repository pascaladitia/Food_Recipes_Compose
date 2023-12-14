package com.pascal.foodrecipescompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pascal.foodrecipescompose.data.local.dao.FavoritesDao
import com.pascal.foodrecipescompose.data.local.dao.ProfileDao
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity

@Database(
    entities = [FavoritesEntity::class, ProfileEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun profileDao(): ProfileDao
}
