package com.example.grocery_app.viewmodel

import androidx.lifecycle.*
import com.example.grocery_app.data.model.Category
import com.example.grocery_app.data.model.Product
import com.example.grocery_app.data.repository.CartRepository
import com.example.grocery_app.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    // --- Categories ---
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    // --- Products ---
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // --- Selected Category ---
    private val _selectedCategory = MutableLiveData("All")
    val selectedCategory: LiveData<String> = _selectedCategory

    // --- Search Query ---
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    // --- Cart count from DB ---
    val cartItemCount: LiveData<Int?> = cartRepository.totalItemCount.asLiveData()

    init {
        loadCategories()
        loadProducts()
    }

    private fun loadCategories() {
        _categories.value = productRepository.getCategories()
    }

    fun loadProducts() {
        val query = _searchQuery.value ?: ""
        val category = _selectedCategory.value ?: "All"

        val result = if (query.isNotBlank()) {
            productRepository.searchProducts(query)
        } else {
            productRepository.getProductsByCategory(category)
        }
        _products.value = result
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _searchQuery.value = ""

        // Update selection state in list
        val updated = _categories.value?.map {
            it.copy(isSelected = it.name == category)
        }
        _categories.value = updated

        loadProducts()
    }

    fun search(query: String) {
        _searchQuery.value = query
        loadProducts()
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
            // Refresh product quantity display
            refreshProductQuantities()
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            cartRepository.removeOneFromCart(product.id)
            refreshProductQuantities()
        }
    }

    private suspend fun refreshProductQuantities() {
        val currentProducts = _products.value ?: return
        val updatedProducts = currentProducts.map { product ->
            val qty = cartRepository.getCartItemCount(product.id)
            product.copy(quantity = qty)
        }
        _products.postValue(updatedProducts)
    }

    fun refreshOnResume() {
        viewModelScope.launch {
            refreshProductQuantities()
        }
    }
}

// Factory for HomeViewModel
class HomeViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(productRepository, cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
