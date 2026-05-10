package com.example.grocery_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_app.R
import com.example.grocery_app.data.model.Product
import com.google.android.material.button.MaterialButton

class ProductAdapter(
    private val onAddClick: (Product) -> Unit,
    private val onMinusClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEmoji: TextView = itemView.findViewById(R.id.tvProductEmoji)
        private val tvName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvUnit: TextView = itemView.findViewById(R.id.tvProductUnit)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val tvOriginalPrice: TextView = itemView.findViewById(R.id.tvOriginalPrice)
        private val tvDiscount: TextView = itemView.findViewById(R.id.tvDiscount)
        private val btnAdd: MaterialButton = itemView.findViewById(R.id.btnAdd)
        private val layoutCounter: View = itemView.findViewById(R.id.layoutCounter)
        private val btnMinus: TextView = itemView.findViewById(R.id.btnMinus)
        private val btnPlus: TextView = itemView.findViewById(R.id.btnPlus)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)

        fun bind(product: Product) {
            tvEmoji.text = product.emoji
            tvName.text = product.name
            tvUnit.text = product.unit
            tvPrice.text = "₹${product.price.toInt()}"

            // Original price (strikethrough)
            if (product.originalPrice != null) {
                tvOriginalPrice.visibility = View.VISIBLE
                tvOriginalPrice.text = "₹${product.originalPrice.toInt()}"
                tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or
                    android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvOriginalPrice.visibility = View.GONE
            }

            // Discount badge
            if (product.hasDiscount) {
                tvDiscount.visibility = View.VISIBLE
                tvDiscount.text = product.discountLabel
            } else {
                tvDiscount.visibility = View.GONE
            }

            // Counter vs Add button
            if (product.quantity > 0) {
                btnAdd.visibility = View.GONE
                layoutCounter.visibility = View.VISIBLE
                tvCount.text = product.quantity.toString()
            } else {
                btnAdd.visibility = View.VISIBLE
                layoutCounter.visibility = View.GONE
            }

            btnAdd.setOnClickListener {
                val bounce = AnimationUtils.loadAnimation(itemView.context, R.anim.bounce)
                it.startAnimation(bounce)
                onAddClick(product)
            }

            btnPlus.setOnClickListener {
                onAddClick(product)
            }

            btnMinus.setOnClickListener {
                onMinusClick(product)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem && oldItem.quantity == newItem.quantity
    }
}
