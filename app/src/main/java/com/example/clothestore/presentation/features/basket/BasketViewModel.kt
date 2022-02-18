package com.example.clothestore.presentation.features.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.data.model.Product
import com.example.clothestore.data.response.Response
import com.example.clothestore.domain.usecase.GetCatalogueUseCase
import com.example.clothestore.presentation.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BasketViewModel @Inject constructor(
    private val useCase: GetCatalogueUseCase
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _totalAmount = MutableLiveData<String>()
    val totalAmount: LiveData<String> get() = _totalAmount


    /**
     * Method to get basket product items from DB
     */
    fun getBasketItems() = viewModelScope.launch {
        useCase.getBasketItems().collect { items ->
            _cartItems.value = items
        }
    }

    /**
     * Method to add/update basket items
     */
    fun addToBasket(product: Product) = viewModelScope.launch {
        val item = async {
            useCase.getBasketProductById(product.productId)
        }
        val targetItem = item.await()
        if (null == targetItem) {
            val cartItem = CartItem(
                product_id = product.productId,
                product_price = product.price,
                product_name = product.name,
                image = product.image,
                count = 1
            )
            useCase.addToBasket(cartItem)
        } else {
            val matchFound = targetItem.product_id == product.productId
            if (matchFound) {
                useCase.updateBasketItem(targetItem.product_id, ++targetItem.count)
            }
        }
    }

    /**
     * Method to delete/update basket items
     */
    fun deleteFromBasket(cartItem: CartItem) = viewModelScope.launch {
        val item = async {
            useCase.getBasketProductById(cartItem.product_id)
        }
        val targetItem = item.await()
        if (targetItem.count == 1) {
            useCase.deleteFromBasket(cartItem.product_id)
        } else {
            useCase.updateBasketItem(targetItem.product_id, --targetItem.count)
        }
    }

    /**
     * Method to get total amount of cart items
     */
    fun getItemsPriceTotal() = viewModelScope.launch {
        val priceMap = mutableMapOf<String, Double>()
        val cartList = mutableListOf<CartItem>()
        _cartItems.value?.let { cartList.addAll(it) }
        cartList.let { itemList ->
            itemList.forEach { item ->
                priceMap[item.product_id] = item.product_price
            }
        }
        var totalPrice = 0.0
        priceMap.forEach { (product_id, price) ->
            totalPrice += price * (cartList.find { it.product_id == product_id }?.count
                ?: 1)
        }
        _totalAmount.postValue(Utils.formatTotalPrice(totalPrice))
    }
}
