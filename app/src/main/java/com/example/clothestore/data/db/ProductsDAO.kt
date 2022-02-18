package com.example.clothestore.data.db

import androidx.room.*
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: Product)

    @Query("SELECT * FROM products")
    fun getFavoriteProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products where product_id = :product_id ")
    fun getWishListProductById(product_id: String): Product

    @Delete
    suspend fun deleteFromFavorites(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBasket(cartItem: CartItem)

    @Query("SELECT * FROM cart_item")
    fun getBasketProducts(): Flow<List<CartItem>>

    @Query("DELETE FROM cart_item WHERE product_id = :product_id")
    suspend fun deleteFromBasket(product_id: String)

    @Query("UPDATE cart_item SET count = :count WHERE product_id = :product_id")
    suspend fun updateBasketItem(product_id: String, count: Int)

    @Query("SELECT * FROM cart_item where product_id = :product_id ")
    suspend fun getBasketProductById(product_id: String): CartItem

}