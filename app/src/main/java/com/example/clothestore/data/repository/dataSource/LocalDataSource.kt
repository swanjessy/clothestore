package com.example.clothestore.data.repository.dataSource

import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun addToFavorites(product: Product)
    fun getFavoriteProducts(): Flow<List<Product>>
    suspend fun deleteFromFavorites(product: Product)
    fun getWishListProductById(productId: String): Product

    suspend fun addToCart(cartItem: CartItem)
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun removeFromCart(productId: String)
    suspend fun updateCartItem(productId: String, count: Int)
    suspend fun getBasketProductById(productId: String): CartItem

}