package com.example.grocery_app.data.repository

import com.example.grocery_app.data.model.Category
import com.example.grocery_app.data.model.Product

class ProductRepository {

    fun getCategories(): List<Category> = listOf(
        Category(0, "All", "🛒", isSelected = true),
        Category(1, "Vegetables", "🥦"),
        Category(2, "Fruits", "🍎"),
        Category(3, "Dairy", "🥛"),
        Category(4, "Bakery", "🍞"),
        Category(5, "Snacks", "🍿"),
        Category(6, "Beverages", "🧃"),
        Category(7, "Meat", "🥩")
    )

    fun getAllProducts(): List<Product> = listOf(
        // Vegetables
        Product(1, "Fresh Tomatoes", "🍅", 35.0, 50.0, "1 kg", "Vegetables", 30),
        Product(2, "Spinach", "🥬", 25.0, null, "250 g", "Vegetables"),
        Product(3, "Onions", "🧅", 40.0, 55.0, "1 kg", "Vegetables", 27),
        Product(4, "Potatoes", "🥔", 30.0, null, "1 kg", "Vegetables"),
        Product(5, "Broccoli", "🥦", 60.0, 80.0, "500 g", "Vegetables", 25),
        Product(6, "Carrots", "🥕", 45.0, null, "500 g", "Vegetables"),
        Product(7, "Bell Pepper", "🫑", 55.0, 70.0, "250 g", "Vegetables", 21),
        Product(8, "Cucumber", "🥒", 20.0, null, "1 pc", "Vegetables"),

        // Fruits
        Product(9, "Red Apples", "🍎", 120.0, 150.0, "1 kg", "Fruits", 20),
        Product(10, "Bananas", "🍌", 50.0, null, "Dozen", "Fruits"),
        Product(11, "Mangoes", "🥭", 80.0, 100.0, "1 kg", "Fruits", 20),
        Product(12, "Grapes", "🍇", 90.0, 110.0, "500 g", "Fruits", 18),
        Product(13, "Watermelon", "🍉", 60.0, null, "1 pc", "Fruits"),
        Product(14, "Oranges", "🍊", 70.0, 85.0, "1 kg", "Fruits", 18),

        // Dairy
        Product(15, "Full Cream Milk", "🥛", 60.0, null, "1 litre", "Dairy"),
        Product(16, "Cheddar Cheese", "🧀", 180.0, 220.0, "200 g", "Dairy", 18),
        Product(17, "Salted Butter", "🧈", 55.0, null, "100 g", "Dairy"),
        Product(18, "Greek Yogurt", "🫙", 80.0, 95.0, "400 g", "Dairy", 16),

        // Bakery
        Product(19, "Whole Wheat Bread", "🍞", 45.0, 55.0, "400 g", "Bakery", 18),
        Product(20, "Croissant", "🥐", 35.0, null, "2 pcs", "Bakery"),
        Product(21, "Multigrain Bun", "🫓", 40.0, 50.0, "Pack of 4", "Bakery", 20),

        // Snacks
        Product(22, "Popcorn", "🍿", 40.0, null, "150 g", "Snacks"),
        Product(23, "Potato Chips", "🥔", 30.0, 40.0, "100 g", "Snacks", 25),
        Product(24, "Dark Chocolate", "🍫", 120.0, 150.0, "80 g", "Snacks", 20),

        // Beverages
        Product(25, "Orange Juice", "🧃", 90.0, 110.0, "1 litre", "Beverages", 18),
        Product(26, "Green Tea", "🍵", 130.0, null, "25 bags", "Beverages"),
        Product(27, "Sparkling Water", "💧", 50.0, null, "1 litre", "Beverages"),

        // Meat
        Product(28, "Chicken Breast", "🥩", 250.0, 300.0, "500 g", "Meat", 17),
        Product(29, "Eggs", "🥚", 90.0, null, "12 pcs", "Meat"),
        Product(30, "Salmon", "🐟", 350.0, 420.0, "250 g", "Meat", 17)
    )

    fun getProductsByCategory(category: String): List<Product> {
        return if (category == "All") getAllProducts()
        else getAllProducts().filter { it.category == category }
    }

    fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return getAllProducts()
        return getAllProducts().filter {
            it.name.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true)
        }
    }
}