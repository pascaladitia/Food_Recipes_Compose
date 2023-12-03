package com.pascal.foodrecipescompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pascal.foodrecipescompose.data.local.dao.FavoritesDao
import com.pascal.foodrecipescompose.data.local.model.FavoritesEntity

@Database(entities = [FavoritesEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
