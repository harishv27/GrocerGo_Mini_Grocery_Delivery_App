package com.example.grocery_app.viewmodel

import androidx.lifecycle.*
import com.example.grocery_app.data.model.CartItem
import com.example.grocery_app.data.model.Order
import com.example.grocery_app.data.repository.CartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

sealed class CheckoutState {
    object Idle : CheckoutState()
    object Loading : CheckoutState()
    data class Success(val order: Order) : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _checkoutState = MutableLiveData<CheckoutState>(CheckoutState.Idle)
    val checkoutState: LiveData<CheckoutState> = _checkoutState

    // Payment method: "COD" or "Online"
    private val _paymentMethod = MutableLiveData("COD")
    val paymentMethod: LiveData<String> = _paymentMethod

    fun selectPayment(method: String) {
        _paymentMethod.value = method
    }

    fun placeOrder(
        name: String,
        phone: String,
        address: String,
        city: String,
        pincode: String,
        cartItems: List<CartItem>,
        subtotal: Double,
        deliveryFee: Double
    ) {
        // Validation
        when {
            name.isBlank() -> {
                _checkoutState.value = CheckoutState.Error("Please enter your name")
                return
            }
            phone.length != 10 -> {
                _checkoutState.value = CheckoutState.Error("Enter a valid 10-digit mobile number")
                return
            }
            address.isBlank() -> {
                _checkoutState.value = CheckoutState.Error("Please enter your address")
                return
            }
            city.isBlank() -> {
                _checkoutState.value = CheckoutState.Error("Please enter your city")
                return
            }
            pincode.length != 6 -> {
                _checkoutState.value = CheckoutState.Error("Enter a valid 6-digit pincode")
                return
            }
        }

        _checkoutState.value = CheckoutState.Loading

        viewModelScope.launch {
            delay(1500) // Simulate network call

            val order = Order(
                orderId = "#GG${(100000..999999).random()}",
                items = cartItems,
                subtotal = subtotal,
                deliveryFee = deliveryFee,
                total = subtotal + deliveryFee,
                name = name,
                phone = phone,
                address = "$address, $city - $pincode",
                city = city,
                pincode = pincode,
                paymentMethod = _paymentMethod.value ?: "COD"
            )

            // Clear cart after successful order
            cartRepository.clearCart()

            _checkoutState.value = CheckoutState.Success(order)
        }
    }
}

class CheckoutViewModelFactory(
    private val cartRepository: CartRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
