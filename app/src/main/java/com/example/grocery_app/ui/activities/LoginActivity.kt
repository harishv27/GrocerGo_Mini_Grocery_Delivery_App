package com.example.grocery_app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grocery_app.R
import com.example.grocery_app.utils.PreferenceManager
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var etMobile: EditText
    private lateinit var tvMobileError: TextView
    private lateinit var btnSendOtp: MaterialButton
    private lateinit var layoutOtp: LinearLayout
    private lateinit var tvOtpSentTo: TextView
    private lateinit var etOtp1: EditText
    private lateinit var etOtp2: EditText
    private lateinit var etOtp3: EditText
    private lateinit var etOtp4: EditText
    private lateinit var tvOtpError: TextView
    private lateinit var btnVerifyOtp: MaterialButton
    private lateinit var tvResendOtp: TextView

    private val FAKE_OTP = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        etMobile = findViewById(R.id.etMobile)
        tvMobileError = findViewById(R.id.tvMobileError)
        btnSendOtp = findViewById(R.id.btnSendOtp)
        layoutOtp = findViewById(R.id.layoutOtp)
        tvOtpSentTo = findViewById(R.id.tvOtpSentTo)
        etOtp1 = findViewById(R.id.etOtp1)
        etOtp2 = findViewById(R.id.etOtp2)
        etOtp3 = findViewById(R.id.etOtp3)
        etOtp4 = findViewById(R.id.etOtp4)
        tvOtpError = findViewById(R.id.tvOtpError)
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp)
        tvResendOtp = findViewById(R.id.tvResendOtp)
    }

    private fun setupListeners() {
        btnSendOtp.setOnClickListener {
            val mobile = etMobile.text.toString().trim()
            if (validateMobile(mobile)) {
                showOtpSection(mobile)
            }
        }

        btnVerifyOtp.setOnClickListener {
            val otp = "${etOtp1.text}${etOtp2.text}${etOtp3.text}${etOtp4.text}"
            verifyOtp(otp)
        }

        tvResendOtp.setOnClickListener {
            Toast.makeText(this, "OTP resent! Use 1234", Toast.LENGTH_SHORT).show()
            clearOtpFields()
        }

        // Auto-focus OTP boxes
        setupOtpAutoFocus()
    }

    private fun validateMobile(mobile: String): Boolean {
        return when {
            mobile.isEmpty() -> {
                showMobileError("Please enter your mobile number")
                false
            }
            mobile.length != 10 -> {
                showMobileError("Enter a valid 10-digit number")
                false
            }
            !mobile.all { it.isDigit() } -> {
                showMobileError("Enter digits only")
                false
            }
            else -> {
                hideMobileError()
                true
            }
        }
    }

    private fun showMobileError(msg: String) {
        tvMobileError.text = msg
        tvMobileError.visibility = View.VISIBLE
    }

    private fun hideMobileError() {
        tvMobileError.visibility = View.GONE
    }

    private fun showOtpSection(mobile: String) {
        tvOtpSentTo.text = "OTP sent to +91 $mobile"
        layoutOtp.visibility = View.VISIBLE
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        layoutOtp.startAnimation(slideIn)
        btnSendOtp.text = "Change Number"
        btnSendOtp.setOnClickListener {
            layoutOtp.visibility = View.GONE
            setupSendOtpListener()
        }
        etOtp1.requestFocus()
    }

    private fun setupSendOtpListener() {
        btnSendOtp.text = "Send OTP"
        btnSendOtp.setOnClickListener {
            val mobile = etMobile.text.toString().trim()
            if (validateMobile(mobile)) {
                showOtpSection(mobile)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        if (otp.length < 4) {
            showOtpError("Please enter all 4 digits")
            return
        }
        if (otp == FAKE_OTP) {
            tvOtpError.visibility = View.GONE
            val pref = PreferenceManager(this)
            pref.setLoggedIn(true)
            pref.setMobile(etMobile.text.toString().trim())
            navigateToHome()
        } else {
            showOtpError("Invalid OTP. Use 1234 for testing")
            shakeOtpBoxes()
        }
    }

    private fun showOtpError(msg: String) {
        tvOtpError.text = msg
        tvOtpError.visibility = View.VISIBLE
    }

    private fun shakeOtpBoxes() {
        listOf(etOtp1, etOtp2, etOtp3, etOtp4).forEach { box ->
            val shake = AnimationUtils.loadAnimation(this, R.anim.bounce)
            box.startAnimation(shake)
        }
    }

    private fun clearOtpFields() {
        listOf(etOtp1, etOtp2, etOtp3, etOtp4).forEach { it.text.clear() }
        tvOtpError.visibility = View.GONE
        etOtp1.requestFocus()
    }

    private fun setupOtpAutoFocus() {
        val boxes = listOf(etOtp1, etOtp2, etOtp3, etOtp4)
        for (i in boxes.indices) {
            boxes[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < boxes.size - 1) {
                        boxes[i + 1].requestFocus()
                    } else if (s?.isNullOrEmpty() == true && i > 0) {
                        boxes[i - 1].requestFocus()
                    }
                    // Auto-verify when last box filled
                    if (i == 3 && s?.length == 1) {
                        val otp = boxes.joinToString("") { it.text.toString() }
                        if (otp.length == 4) verifyOtp(otp)
                    }
                }
            })
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_bottom, android.R.anim.fade_out)
        finish()
    }
}
