package com.pascal.foodrecipescompose.data.setup

import com.pascal.foodrecipescompose.data.remote.AppService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppServiceFactory @Inject constructor(private val httpClientFactory: HttpClientFactory) {

    fun getAppService(serviceFactory : ServiceFactory): AppService {
        val httpClient = httpClientFactory.abstractClient.newBuilder()
            .addInterceptor(NetworkModule.basicHeaderInterceptor())
            .build()
        val appService = serviceFactory.retrofitInstance.newBuilder().client(httpClient).build()
        return appService.create(AppService::class.java)
    }

}

