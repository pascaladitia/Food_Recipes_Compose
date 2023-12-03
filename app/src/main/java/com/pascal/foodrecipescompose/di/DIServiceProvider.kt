package com.pascal.foodrecipescompose.di

import android.content.Context
import androidx.room.Room
import com.pascal.foodrecipescompose.data.local.LocalDataSource
import com.pascal.foodrecipescompose.data.local.database.AppDatabase
import com.pascal.foodrecipescompose.data.local.database.DatabaseConstants
import com.pascal.foodrecipescompose.data.remote.AppService
import com.pascal.foodrecipescompose.data.setup.AppServiceFactory
import com.pascal.foodrecipescompose.data.setup.HttpClientFactory
import com.pascal.foodrecipescompose.data.setup.ServiceFactory
import com.pascal.foodrecipescompose.di.NetworkConfig.BASE_DOMAIN
import com.pascal.foodrecipescompose.di.NetworkConfig.allowedSSlFingerprints
import com.pascal.foodrecipescompose.domain.repository.IRepository
import com.pascal.foodrecipescompose.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIServiceProvider {
    @Singleton
    @AppMainDB
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DatabaseConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClientFactory(): HttpClientFactory {
        return HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints)
    }

    @Singleton
    @Provides
    fun provideServiceFactory(): ServiceFactory {
        return ServiceFactory(NetworkConfig.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideService(httpClientFactory: HttpClientFactory, serviceFactory : ServiceFactory): AppService {
        return AppServiceFactory(httpClientFactory).getAppService(serviceFactory)
    }

    @Singleton
    @Provides
    fun provideRepository(appService: AppService, localDataSource: LocalDataSource): IRepository {
        return Repository(appService, localDataSource)
    }

}