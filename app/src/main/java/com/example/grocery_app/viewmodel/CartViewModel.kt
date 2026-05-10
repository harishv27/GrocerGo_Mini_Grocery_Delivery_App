package com.example.grocery_app.viewmodel

import androidx.lifecycle.*
import com.example.grocery_app.data.model.CartItem
import com.example.grocery_app.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CartUiState {
    object Loading : CartUiState()
    object Empty : CartUiState()
    data class Success(val items: List<CartItem>) : CartUiState()
}

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    // StateFlow for cart UI state
    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val cartUiState: StateFlow<CartUiState> = _cartUiState.asStateFlow()

    // LiveData for cart items
    val cartItems: LiveData<List<CartItem>> = cartRepository.cartItems.asLiveData()

    // LiveData for totals
    val totalItemCount: LiveData<Int?> = cartRepository.totalItemCount.asLiveData()
    val totalPrice: LiveData<Double?> = cartRepository.totalPrice.asLiveData()

    // Delivery logic: free above ₹499
    val deliveryFee: LiveData<Double> = totalPrice.map { price ->
        if ((price ?: 0.0) >= 499.0) 0.0 else 40.0
    }

    val grandTotal: LiveData<Double> = MediatorLiveData<Double>().apply {
        fun update() {
            val sub = totalPrice.value ?: 0.0
            val del = deliveryFee.value ?: 40.0
            value = sub + del
        }
        addSource(totalPrice) { update() }
        addSource(deliveryFee) { update() }
    }

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.cartItems.collect { items ->
                _cartUiState.value = if (items.isEmpty()) CartUiState.Empty
                else CartUiState.Success(items)
            }
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.addToCart(
                com.example.grocery_app.data.model.Product(
                    id = cartItem.productId,
                    name = cartItem.name,
                    emoji = cartItem.emoji,
                    price = cartItem.price,
                    unit = cartItem.unit,
                    category = ""
                )
            )
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeOneFromCart(cartItem.productId)
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartItem.productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}

class CartViewModelFactory(
    private val cartRepository: CartRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
