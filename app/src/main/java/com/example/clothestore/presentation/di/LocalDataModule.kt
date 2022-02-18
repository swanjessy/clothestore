package com.example.clothestore.presentation.di

import com.example.clothestore.data.db.ProductsDAO
import com.example.clothestore.data.repository.dataSource.LocalDataSource
import com.example.clothestore.data.repository.dataSourceImpl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(productsDAO: ProductsDAO): LocalDataSource {
        return LocalDataSourceImpl(productsDAO)
    }

}













