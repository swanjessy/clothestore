package com.example.clothestore.presentation.di

import com.example.clothestore.data.api.APIService
import com.example.clothestore.data.repository.dataSource.RemoteDataSource
import com.example.clothestore.data.repository.dataSourceImpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideCatalogueRemoteDataSource(
        apiService: APIService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

}












