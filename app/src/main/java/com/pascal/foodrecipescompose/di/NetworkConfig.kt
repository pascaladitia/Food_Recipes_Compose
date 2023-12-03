package com.pascal.foodrecipescompose.di

object NetworkConfig {
    const val BASE_DOMAIN = "www.themealdb.com/api/json/v1/1/"
    const val BASE_URL = "https://$BASE_DOMAIN/"
    val allowedSSlFingerprints = emptyList<String>()
}