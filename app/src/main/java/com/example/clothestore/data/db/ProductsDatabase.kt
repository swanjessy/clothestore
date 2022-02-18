package com.example.clothestore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product

@Database(
    entities = [Product::class, CartItem::class],
    version = 2,
    exportSchema = false
)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun getProductsDAO(): ProductsDAO
}

