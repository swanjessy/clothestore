package com.example.clothestore.domain.usecase

import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import com.example.clothestore.domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatalogueUseCase @Inject constructor(private val catalogueRepository: CatalogueRepository) {

    suspend fun getCatalogue() = catalogueRepository.getCatalogue()

    suspend fun addFavourites(product: Product) = catalogueRepository.addToFavorites(product)

    suspend fun deleteFavourite(product: Product) = catalogueRepository.deleteFromFavorites(product)

    fun getFavourites(): Flow<List<Product>> {
        return catalogueRepository.getFavoriteProducts()
    }

    suspend fun addToBasket(cartItem: CartItem) =
        catalogueRepository.addToCart(cartItem)

    suspend fun deleteFromBasket(productId: String) =
        catalogueRepository.removeFromCart(productId)

    fun getBasketItems(): Flow<List<CartItem>> {
        return catalogueRepository.getCartItems()
    }

    suspend fun updateBasketItem(productId: String, count: Int) =
        catalogueRepository.updateCartItem(productId, count)

    suspend fun getBasketProductById(productId: String): CartItem =
        catalogueRepository.getBasketProductById(productId)

    fun getWishListProductById(productId: String): Product =
        catalogueRepository.getWishListProductById(productId)

}