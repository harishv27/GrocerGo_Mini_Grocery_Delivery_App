package com.example.grocery_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val name: String,
    val emoji: String,
    val price: Double,
    val unit: String,
    var quantity: Int
) : Parcelable {
    val totalPrice: Double
        get() = price * quantity
}