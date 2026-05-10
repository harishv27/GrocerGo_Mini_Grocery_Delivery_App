package com.example.grocery_app.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grocery_app.R
import com.example.grocery_app.data.db.AppDatabase
import com.example.grocery_app.data.repository.CartRepository
import com.example.grocery_app.data.repository.ProductRepository
import com.example.grocery_app.ui.fragments.CartFragment
import com.example.grocery_app.ui.fragments.HomeFragment
import com.example.grocery_app.viewmodel.CartViewModel
import com.example.grocery_app.viewmodel.CartViewModelFactory
import com.example.grocery_app.viewmodel.HomeViewModel
import com.example.grocery_app.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var cartViewModel: CartViewModel
    lateinit var homeViewModel: HomeViewModel

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tvCartBadge: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModels()
        initViews()
        setupBottomNav()
        observeCart()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun initViewModels() {
        val db = AppDatabase.getDatabase(this)
        val cartRepository = CartRepository(db.cartDao())
        val productRepository = ProductRepository()

        cartViewModel = ViewModelProvider(
            this,
            CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(productRepository, cartRepository)
        )[HomeViewModel::class.java]
    }

    private fun initViews() {
        bottomNav = findViewById(R.id.bottomNav)
        tvCartBadge = findViewById(R.id.tvCartBadge)
    }

    private fun setupBottomNav() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun observeCart() {
        cartViewModel.totalItemCount.observe(this) { count ->
            val cartCount = count ?: 0
            if (cartCount > 0) {
                tvCartBadge.visibility = View.VISIBLE
                tvCartBadge.text = if (cartCount > 9) "9+" else cartCount.toString()
            } else {
                tvCartBadge.visibility = View.GONE
            }
        }
    }

    fun navigateToCart() {
        bottomNav.selectedItemId = R.id.nav_cart
    }

    fun navigateToHome() {
        bottomNav.selectedItemId = R.id.nav_home
    }
}
