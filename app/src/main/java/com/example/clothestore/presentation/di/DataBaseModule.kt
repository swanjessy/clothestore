package com.example.clothestore.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.clothestore.data.db.ProductsDAO
import com.example.clothestore.data.db.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideProductsDatabase(app: Application): ProductsDatabase {
        return Room.databaseBuilder(app, ProductsDatabase::class.java, "product_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(productsDatabase: ProductsDatabase): ProductsDAO {
        return productsDatabase.getProductsDAO()
    }

}