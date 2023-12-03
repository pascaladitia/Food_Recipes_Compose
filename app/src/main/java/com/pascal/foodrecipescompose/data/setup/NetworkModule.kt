package com.pascal.foodrecipescompose.data.setup

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {

    fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun basicHeaderInterceptor() = Interceptor {
        val req = it.request()
        val query = req.url
            .newBuilder()
            .build()
        it.proceed(req.newBuilder().url(query).build())
    }

}