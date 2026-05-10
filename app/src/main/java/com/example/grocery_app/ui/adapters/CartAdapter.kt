package com.example.grocery_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_app.R
import com.example.grocery_app.data.model.CartItem
import com.example.grocery_app.utils.CurrencyUtils

class CartAdapter(
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEmoji: TextView = itemView.findViewById(R.id.tvCartItemEmoji)
        private val tvName: TextView = itemView.findViewById(R.id.tvCartItemName)
        private val tvUnit: TextView = itemView.findViewById(R.id.tvCartItemUnit)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvCartItemPrice)
        private val tvQty: TextView = itemView.findViewById(R.id.tvCartQty)
        private val btnPlus: TextView = itemView.findViewById(R.id.btnCartPlus)
        private val btnMinus: TextView = itemView.findViewById(R.id.btnCartMinus)
        private val btnRemove: TextView = itemView.findViewById(R.id.btnRemove)

        fun bind(item: CartItem) {
            tvEmoji.text = item.emoji
            tvName.text = item.name
            tvUnit.text = item.unit
            tvPrice.text = CurrencyUtils.format(item.totalPrice)
            tvQty.text = item.quantity.toString()

            btnPlus.setOnClickListener { onIncrease(item) }
            btnMinus.setOnClickListener { onDecrease(item) }
            btnRemove.setOnClickListener { onRemove(item) }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem == newItem
    }
}
