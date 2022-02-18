package com.example.clothestore.data.repository

import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import com.example.clothestore.data.repository.dataSource.LocalDataSource
import com.example.clothestore.data.repository.dataSource.RemoteDataSource
import com.example.clothestore.domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogueRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CatalogueRepository {

    override suspend fun getCatalogue() = remoteDataSource.getCatalogue()

    override suspend fun addToFavorites(product: Product) {
        localDataSource.addToFavorites(product)
    }

    override suspend fun deleteFromFavorites(product: Product) {
        localDataSource.deleteFromFavorites(product)
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return localDataSource.getFavoriteProducts()
    }

    override fun getWishListProductById(productId: String): Product {
        return localDataSource.getWishListProductById(productId)
    }

    override suspend fun addToCart(cartItem: CartItem) {
        localDataSource.addToCart(cartItem)
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return localDataSource.getCartItems()
    }

    override suspend fun removeFromCart(productId: String) {
        localDataSource.removeFromCart(productId)
    }

    override suspend fun updateCartItem(productId: String, count: Int) {
        if (count == 0) {
            localDataSource.removeFromCart(productId)
        } else localDataSource.updateCartItem(productId, count)
    }

    override suspend fun getBasketProductById(productId: String): CartItem {
        return localDataSource.getBasketProductById(productId)
    }

}