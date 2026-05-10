package com.example.grocery_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val emoji: String,
    val price: Double,
    val originalPrice: Double? = null,
    val unit: String,
    val category: String,
    val discountPercent: Int = 0,
    var quantity: Int = 0
) {
    val discountLabel: String
        get() = if (discountPercent > 0) "$discountPercent% OFF" else ""

    val hasDiscount: Boolean
        get() = discountPercent > 0
}
