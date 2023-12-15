package com.pascal.foodrecipescompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_PROFILE)
data class ProfileEntity (
    @PrimaryKey
    val id: Int? = null,
    val name: String? = "",
    val imagePath: String? = "",
    val imageProfilePath: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val address: String? = ""
    )
