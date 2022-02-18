package com.example.clothestore.presentation.di

import com.example.clothestore.domain.repository.CatalogueRepository
import com.example.clothestore.domain.usecase.GetCatalogueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideCatalogueCase(catalogueRepository: CatalogueRepository): GetCatalogueUseCase {
        return GetCatalogueUseCase(catalogueRepository)
    }

}


















