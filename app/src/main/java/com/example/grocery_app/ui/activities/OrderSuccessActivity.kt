package com.example.grocery_app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.grocery_app.R
import com.example.grocery_app.utils.CurrencyUtils
import com.google.android.material.button.MaterialButton

class OrderSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        val orderId = intent.getStringExtra("order_id") ?: "#GG000000"
        val total = intent.getDoubleExtra("total", 0.0)
        val paymentMethod = intent.getStringExtra("payment_method") ?: "COD"
        val address = intent.getStringExtra("address") ?: ""

        // Bind views
        val tvOrderId = findViewById<TextView>(R.id.tvOrderId)
        val tvOrderAmount = findViewById<TextView>(R.id.tvOrderAmount)
        val tvPaymentMode = findViewById<TextView>(R.id.tvPaymentMode)
        val tvOrderAddress = findViewById<TextView>(R.id.tvOrderAddress)
        val tvSuccessEmoji = findViewById<TextView>(R.id.tvSuccessEmoji)
        val btnTrackOrder = findViewById<MaterialButton>(R.id.btnTrackOrder)
        val btnContinueShopping = findViewById<MaterialButton>(R.id.btnContinueShopping)

        tvOrderId.text = orderId
        tvOrderAmount.text = CurrencyUtils.format(total)
        tvPaymentMode.text = paymentMethod
        tvOrderAddress.text = address

        // Animate checkmark
        val scaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_in)
        tvSuccessEmoji.startAnimation(scaleIn)

        btnTrackOrder.setOnClickListener {
            // In a real app this would navigate to tracking
            navigateToHome()
        }

        btnContinueShopping.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onBackPressed() {
        navigateToHome()
    }
}
