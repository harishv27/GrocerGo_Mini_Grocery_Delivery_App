package com.example.grocery_app.data.repository

import com.example.grocery_app.data.db.CartDao
import com.example.grocery_app.data.model.CartItem
import com.example.grocery_app.data.model.Product
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val cartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()
    val totalItemCount: Flow<Int?> = cartDao.getTotalItemCount()
    val totalPrice: Flow<Double?> = cartDao.getTotalPrice()

    suspend fun addToCart(product: Product) {
        val existing = cartDao.getCartItem(product.id)
        if (existing != null) {
            cartDao.updateQuantity(product.id, existing.quantity + 1)
        } else {
            cartDao.insertOrUpdate(
                CartItem(
                    productId = product.id,
                    name = product.name,
                    emoji = product.emoji,
                    price = product.price,
                    unit = product.unit,
                    quantity = 1
                )
            )
        }
    }

    suspend fun removeOneFromCart(productId: Int) {
        val existing = cartDao.getCartItem(productId) ?: return
        if (existing.quantity <= 1) {
            cartDao.delete(existing)
        } else {
            cartDao.updateQuantity(productId, existing.quantity - 1)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        val existing = cartDao.getCartItem(productId) ?: return
        cartDao.delete(existing)
    }

    suspend fun getCartItem(productId: Int): CartItem? = cartDao.getCartItem(productId)

    suspend fun clearCart() = cartDao.clearCart()

    suspend fun getCartItemCount(productId: Int): Int {
        return cartDao.getCartItem(productId)?.quantity ?: 0
    }
}