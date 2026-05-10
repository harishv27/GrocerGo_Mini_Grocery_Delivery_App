package com.example.grocery_app.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.grocery_app.R
import com.example.grocery_app.data.db.AppDatabase
import com.example.grocery_app.data.model.CartItem
import com.example.grocery_app.data.model.Order
import com.example.grocery_app.data.repository.CartRepository
import com.example.grocery_app.utils.CurrencyUtils
import com.example.grocery_app.viewmodel.CheckoutState
import com.example.grocery_app.viewmodel.CheckoutViewModel
import com.example.grocery_app.viewmodel.CheckoutViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: CheckoutViewModel

    private lateinit var etName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etCity: TextInputEditText
    private lateinit var etPincode: TextInputEditText
    private lateinit var rbCod: RadioButton
    private lateinit var rbOnline: RadioButton
    private lateinit var cardCod: MaterialCardView
    private lateinit var cardOnline: MaterialCardView
    private lateinit var tvSummaryItems: TextView
    private lateinit var tvSummarySubtotal: TextView
    private lateinit var tvSummaryDelivery: TextView
    private lateinit var tvSummaryTotal: TextView
    private lateinit var btnPlaceOrder: MaterialButton
    private lateinit var btnBack: TextView

    private var cartItems: List<CartItem> = emptyList()
    private var subtotal = 0.0
    private var deliveryFee = 0.0
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)



        // Get data from intent
        subtotal = intent.getDoubleExtra("subtotal", 0.0)
        deliveryFee = intent.getDoubleExtra("delivery_fee", 0.0)
        cartItems = intent.getParcelableArrayListExtra("cart_items") ?: emptyList()  // ← Parcelable read

        initViewModels()
        initViews()
        setupListeners()
        populateSummary()
    }

    private fun initViewModels() {
        val db = AppDatabase.getDatabase(this)
        val cartRepository = CartRepository(db.cartDao())
        viewModel = ViewModelProvider(
            this,
            CheckoutViewModelFactory(cartRepository)
        )[CheckoutViewModel::class.java]
    }

    private fun initViews() {
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        etCity = findViewById(R.id.etCity)
        etPincode = findViewById(R.id.etPincode)
        rbCod = findViewById(R.id.rbCod)
        rbOnline = findViewById(R.id.rbOnline)
        cardCod = findViewById(R.id.cardCod)
        cardOnline = findViewById(R.id.cardOnline)
        tvSummaryItems = findViewById(R.id.tvSummaryItems)
        tvSummarySubtotal = findViewById(R.id.tvSummarySubtotal)
        tvSummaryDelivery = findViewById(R.id.tvSummaryDelivery)
        tvSummaryTotal = findViewById(R.id.tvSummaryTotal)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }

        cardCod.setOnClickListener {
            rbCod.isChecked = true
            rbOnline.isChecked = false
            viewModel.selectPayment("COD")
            updatePaymentUI("COD")
        }

        cardOnline.setOnClickListener {
            rbOnline.isChecked = true
            rbCod.isChecked = false
            viewModel.selectPayment("Online")
            updatePaymentUI("Online")
        }

        btnPlaceOrder.setOnClickListener {
            viewModel.placeOrder(
                name = etName.text.toString(),
                phone = etPhone.text.toString(),
                address = etAddress.text.toString(),
                city = etCity.text.toString(),
                pincode = etPincode.text.toString(),
                cartItems = cartItems,
                subtotal = subtotal,
                deliveryFee = deliveryFee
            )
        }

        viewModel.checkoutState.observe(this) { state ->
            when (state) {
                is CheckoutState.Loading -> showLoading()
                is CheckoutState.Success -> {
                    hideLoading()
                    navigateToSuccess(state.order)
                }
                is CheckoutState.Error -> {
                    hideLoading()
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> hideLoading()
            }
        }
    }

    private fun populateSummary() {
        val totalItems = cartItems.sumOf { it.quantity }
        tvSummaryItems.text = "$totalItems item${if (totalItems > 1) "s" else ""}"
        tvSummarySubtotal.text = CurrencyUtils.format(subtotal)
        tvSummaryDelivery.text = if (deliveryFee == 0.0) "FREE" else CurrencyUtils.format(deliveryFee)
        tvSummaryTotal.text = CurrencyUtils.format(subtotal + deliveryFee)
    }

    private fun updatePaymentUI(selected: String) {
        if (selected == "COD") {
            cardCod.strokeColor = getColor(R.color.primary)
            cardCod.setCardBackgroundColor(getColor(R.color.primary_light))
            cardOnline.strokeColor = getColor(R.color.gray_200)
            cardOnline.setCardBackgroundColor(getColor(R.color.white))
        } else {
            cardOnline.strokeColor = getColor(R.color.primary)
            cardOnline.setCardBackgroundColor(getColor(R.color.primary_light))
            cardCod.strokeColor = getColor(R.color.gray_200)
            cardCod.setCardBackgroundColor(getColor(R.color.white))
        }
    }

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(this)
            loadingDialog?.setContentView(R.layout.dialog_order_loading)
            loadingDialog?.setCancelable(false)
            loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun navigateToSuccess(order: Order) {
        val intent = Intent(this, OrderSuccessActivity::class.java).apply {
            putExtra("order_id", order.orderId)
            putExtra("total", order.total)
            putExtra("payment_method", order.paymentMethod)
            putExtra("address", order.address)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        overridePendingTransition(R.anim.scale_in, android.R.anim.fade_out)
        finish()
    }
}
