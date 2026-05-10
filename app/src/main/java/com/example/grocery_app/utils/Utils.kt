package com.example.grocery_app.utils

import java.util.Calendar

object CurrencyUtils {
    fun format(amount: Double): String {
        return "₹${String.format("%.0f", amount)}"
    }
}

object TimeUtils {
    fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Good Morning 🌅"
            in 12..16 -> "Good Afternoon ☀️"
            in 17..20 -> "Good Evening 🌇"
            else -> "Good Night 🌙"
        }
    }
}
