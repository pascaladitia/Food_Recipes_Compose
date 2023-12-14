package com.pascal.foodrecipescompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_PROFILE)
data class ProfileEntity (
    @PrimaryKey
    val id: Int? = null,
    val name: String? = null,
    val imagePath: String? = null,
    val desc: String? = null,
    val work: String? = null,
    val address: String? = null,
    val lat: String? = null,
    val lon: String? = null
    )
