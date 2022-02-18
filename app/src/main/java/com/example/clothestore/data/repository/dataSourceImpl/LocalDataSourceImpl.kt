package com.example.clothestore.data.repository.dataSourceImpl

import com.example.clothestore.data.db.ProductsDAO
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import com.example.clothestore.data.repository.dataSource.LocalDataSource
import com.example.clothestore.data.response.Response
import com.example.clothestore.data.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val productsDAO: ProductsDAO
) : LocalDataSource {

    override suspend fun addToFavorites(product: Product) {
        productsDAO.addToFavorites(product)

    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return productsDAO.getFavoriteProducts()
    }

    override suspend fun deleteFromFavorites(product: Product) {
        productsDAO.deleteFromFavorites(product)
    }

    override fun getWishListProductById(productId: String): Product {
        return productsDAO.getWishListProductById(productId)
    }

    override suspend fun addToCart(cartItem: CartItem) {
        productsDAO.addToBasket(cartItem)
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return productsDAO.getBasketProducts()
    }

    override suspend fun removeFromCart(productId: String) {
        productsDAO.deleteFromBasket(productId)
    }

    override suspend fun updateCartItem(productId: String, count: Int) {
        productsDAO.updateBasketItem(productId, count)
    }

    override suspend fun getBasketProductById(productId: String): CartItem {
        return productsDAO.getBasketProductById(productId)
    }


    private suspend fun addToFavoritesResponse(product: Product?): Resource<Response> {
        try {
            product?.let {
                productsDAO.addToFavorites(product)
                return Resource.Success(Response("success"))
            }
            return Resource.Error(
                "Product Not Found", Response("fail")
            )
        } catch (e: Exception) {
            return Resource.Error(e.message.toString(), null)
        }
    }


}