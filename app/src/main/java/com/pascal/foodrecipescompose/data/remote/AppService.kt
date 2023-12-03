package com.pascal.foodrecipescompose.data.remote

import com.pascal.foodrecipescompose.data.remote.dtos.CategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.FilterCategoryResponse
import com.pascal.foodrecipescompose.data.remote.dtos.ListRecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {
    @GET("categories.php")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getFilterCategory(@Query("c") query: String? = null): Response<FilterCategoryResponse>

    @GET("search.php")
    suspend fun getListRecipe(@Query("f") query: String? = null): Response<ListRecipesResponse>

    @GET("search.php")
    suspend fun getSearchRecipe(@Query("s") query: String? = null): Response<ListRecipesResponse>

    @GET("lookup.php")
    suspend fun getDetailRecipe(@Query("i") query: String? = null): Response<ListRecipesResponse>

}