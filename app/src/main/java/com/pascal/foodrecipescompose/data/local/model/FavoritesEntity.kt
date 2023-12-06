package com.pascal.foodrecipescompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_FAVORITES)
data class FavoritesEntity (
    @PrimaryKey
    val id: Int,
    val name: String? = null,
    val imagePath: String? = null,
    val category: String? = null,
    val tags: String? = null,
    val youtubeUrl: String? = null
)
