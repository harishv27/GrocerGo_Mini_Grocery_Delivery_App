package com.example.grocery_app.data.model

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val subtotal: Double,
    val deliveryFee: Double,
    val total: Double,
    val name: String,
    val phone: String,
    val address: String,
    val city: String,
    val pincode: String,
    val paymentMethod: String,
    val timestamp: Long = System.currentTimeMillis()
)