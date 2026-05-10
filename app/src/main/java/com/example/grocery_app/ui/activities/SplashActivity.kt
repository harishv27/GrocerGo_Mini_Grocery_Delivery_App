package com.example.grocery_app.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.grocery_app.R
import com.example.grocery_app.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Animate app icon
        val tvAppIcon = findViewById<TextView>(R.id.tvAppIcon)
        val scaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_in)
        tvAppIcon.startAnimation(scaleIn)

        lifecycleScope.launch {
            delay(2000)
            navigateNext()
        }
    }

    private fun navigateNext() {
        val pref = PreferenceManager(this)
        val intent = if (pref.isLoggedIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
