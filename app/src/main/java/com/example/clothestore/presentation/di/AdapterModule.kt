package com.example.clothestore.presentation.di

import android.app.Application
import com.example.clothestore.presentation.features.basket.BasketAdapter
import com.example.clothestore.presentation.features.catalogue.CatalogueAdapter
import com.example.clothestore.presentation.features.wishlist.WishlistAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideCatalogueAdapter(application: Application): CatalogueAdapter {
        return CatalogueAdapter(application)
    }

    @Singleton
    @Provides
    fun provideWishListAdapter(application: Application): WishlistAdapter {
        return WishlistAdapter(application)
    }

    @Singleton
    @Provides
    fun provideBasketAdapter(application: Application): BasketAdapter {
        return BasketAdapter(application)
    }
}