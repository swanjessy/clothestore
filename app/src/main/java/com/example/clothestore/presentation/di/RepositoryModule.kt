package com.example.clothestore.presentation.di

import com.example.clothestore.data.repository.CatalogueRepositoryImpl
import com.example.clothestore.data.repository.dataSource.LocalDataSource
import com.example.clothestore.data.repository.dataSource.RemoteDataSource
import com.example.clothestore.domain.repository.CatalogueRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCatalogueRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): CatalogueRepository {
        return CatalogueRepositoryImpl(remoteDataSource, localDataSource)
    }

}














