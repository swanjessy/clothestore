package com.example.clothestore.domain.repository

import com.example.clothestore.data.model.APIResponse
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import com.example.clothestore.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogueRepository {

    suspend fun getCatalogue(): Flow<Resource<APIResponse>>

    // Wishlist Repository
    suspend fun addToFavorites(product: Product)
    suspend fun deleteFromFavorites(product: Product)
    fun getFavoriteProducts(): Flow<List<Product>>
    fun getWishListProductById(productId: String): Product

    // Cart Repository
    suspend fun addToCart(cartItem: CartItem)
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun removeFromCart(productId: String)
    suspend fun updateCartItem(productId: String, count: Int)
    suspend fun getBasketProductById(productId: String): CartItem

}