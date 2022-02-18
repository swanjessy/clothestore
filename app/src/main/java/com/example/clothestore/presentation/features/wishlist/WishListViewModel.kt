package com.example.clothestore.presentation.features.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothestore.data.model.Product
import com.example.clothestore.domain.usecase.GetCatalogueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WishListViewModel @Inject constructor(
    private val useCase: GetCatalogueUseCase
) : ViewModel() {

    private val _favItems = MutableLiveData<List<Product>>()
    val favItems: LiveData<List<Product>> get() = _favItems

    /**
     * Method to add product if not present in wishlist
     * Only 1 item of the product will be present in the wishlist
     * @param product : Product
     */
    fun addToWishlist(product: Product) = viewModelScope.launch {
        val item = withContext(Dispatchers.Default) {
            useCase.getWishListProductById(product.productId)
        }
        if (item == null) {
            useCase.addFavourites(product)
        }
    }

    /**
     * Method to get all wishlist products
     */
    fun getFavouriteProducts() = viewModelScope.launch {
        useCase.getFavourites().collect { items ->
            _favItems.postValue(items)
        }
    }

    /**
     * Method to remove wish list when product is added to cart
     * @param product : Product
     */
    fun updateWishList(product: Product) = viewModelScope.launch {
        val item = withContext(Dispatchers.Default) {
            useCase.getWishListProductById(product.productId)
        }
        item.let {
            useCase.deleteFavourite(product)
        }
    }

}
