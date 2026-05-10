package com.example.grocery_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_app.R
import com.example.grocery_app.ui.activities.CheckoutActivity
import com.example.grocery_app.ui.activities.MainActivity
import com.example.grocery_app.ui.adapters.CartAdapter
import com.example.grocery_app.utils.CurrencyUtils
import com.example.grocery_app.viewmodel.CartUiState
import com.example.grocery_app.viewmodel.CartViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    private lateinit var rvCartItems: RecyclerView
    private lateinit var layoutEmptyCart: LinearLayout
    private lateinit var nestedScrollCart: androidx.core.widget.NestedScrollView
    private lateinit var cardBillSummary: MaterialCardView
    private lateinit var cardCheckout: MaterialCardView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvDeliveryFee: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvCartItemCount: TextView
    private lateinit var tvCartTotal: TextView
    private lateinit var btnProceedCheckout: MaterialButton
    private lateinit var btnShopNow: MaterialButton
    private lateinit var tvFreeDeliveryMsg: TextView
    private lateinit var rowSavings: LinearLayout
    private lateinit var tvSavings: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initViewModel()
        setupRecyclerView()
        observeData()
        setupListeners()
    }

    private fun initViews(view: View) {
        rvCartItems = view.findViewById(R.id.rvCartItems)
        layoutEmptyCart = view.findViewById(R.id.layoutEmptyCart)
        nestedScrollCart = view.findViewById(R.id.nestedScrollCart)
        cardBillSummary = view.findViewById(R.id.cardBillSummary)
        cardCheckout = view.findViewById(R.id.cardCheckout)
        tvSubtotal = view.findViewById(R.id.tvSubtotal)
        tvDeliveryFee = view.findViewById(R.id.tvDeliveryFee)
        tvTotal = view.findViewById(R.id.tvTotal)
        tvCartItemCount = view.findViewById(R.id.tvCartItemCount)
        tvCartTotal = view.findViewById(R.id.tvCartTotal)
        btnProceedCheckout = view.findViewById(R.id.btnProceedCheckout)
        btnShopNow = view.findViewById(R.id.btnShopNow)
        tvFreeDeliveryMsg = view.findViewById(R.id.tvFreeDeliveryMsg)
        rowSavings = view.findViewById(R.id.rowSavings)
        tvSavings = view.findViewById(R.id.tvSavings)
    }

    private fun initViewModel() {
        viewModel = (requireActivity() as MainActivity).cartViewModel
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { item -> viewModel.increaseQuantity(item) },
            onDecrease = { item -> viewModel.decreaseQuantity(item) },
            onRemove = { item -> viewModel.removeItem(item) }
        )
        rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeData() {
        // Observe UI state for empty/filled
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartUiState.collect { state ->
                when (state) {
                    is CartUiState.Empty -> showEmptyCart()
                    is CartUiState.Success -> showCartContent()
                    else -> {}
                }
            }
        }

        // Observe items
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
        }

        // Observe totals
        viewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            val sub = price ?: 0.0
            tvSubtotal.text = CurrencyUtils.format(sub)
        }

        viewModel.deliveryFee.observe(viewLifecycleOwner) { fee ->
            tvDeliveryFee.text = if (fee == 0.0) "FREE 🎉" else CurrencyUtils.format(fee)

            // Show free delivery message
            val sub = viewModel.totalPrice.value ?: 0.0
            if (fee > 0) {
                val needed = 499.0 - sub
                tvFreeDeliveryMsg.text = "Add ${CurrencyUtils.format(needed)} more for FREE delivery!"
            } else {
                tvFreeDeliveryMsg.text = "🎉 You've unlocked FREE delivery!"
            }
        }

        viewModel.grandTotal.observe(viewLifecycleOwner) { total ->
            tvTotal.text = CurrencyUtils.format(total)
            tvCartTotal.text = CurrencyUtils.format(total)
        }

        viewModel.totalItemCount.observe(viewLifecycleOwner) { count ->
            val c = count ?: 0
            tvCartItemCount.text = "$c item${if (c != 1) "s" else ""}"
        }
    }

    private fun setupListeners() {
        btnShopNow.setOnClickListener {
            (requireActivity() as MainActivity).navigateToHome()
        }

        // In CartFragment.kt — replace the btnProceedCheckout click listener with this:

        btnProceedCheckout.setOnClickListener {
            val items = viewModel.cartItems.value ?: emptyList()
            val subtotal = viewModel.totalPrice.value ?: 0.0
            val deliveryFee = viewModel.deliveryFee.value ?: 0.0

            val intent = Intent(requireContext(), CheckoutActivity::class.java).apply {
                putExtra("subtotal", subtotal)
                putExtra("delivery_fee", deliveryFee)
                putParcelableArrayListExtra("cart_items", ArrayList(items))  // ← Parcelable, not Serializable
            }
            startActivity(intent)
        }

    }

    private fun showEmptyCart() {
        layoutEmptyCart.visibility = View.VISIBLE
        nestedScrollCart.visibility = View.GONE
        cardCheckout.visibility = View.GONE
    }

    private fun showCartContent() {
        layoutEmptyCart.visibility = View.GONE
        nestedScrollCart.visibility = View.VISIBLE
        cardCheckout.visibility = View.VISIBLE
    }
}
