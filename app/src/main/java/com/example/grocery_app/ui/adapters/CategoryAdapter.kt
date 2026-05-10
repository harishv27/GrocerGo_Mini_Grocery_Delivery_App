package com.example.grocery_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_app.R
import com.example.grocery_app.data.model.Category
import com.google.android.material.card.MaterialCardView

class CategoryAdapter(
    private val onCategoryClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryCard: MaterialCardView = itemView.findViewById(R.id.categoryCard)
        private val tvIcon: TextView = itemView.findViewById(R.id.tvCategoryIcon)
        private val tvName: TextView = itemView.findViewById(R.id.tvCategoryName)

        fun bind(category: Category) {
            tvIcon.text = category.emoji
            tvName.text = category.name

            val context = itemView.context

            if (category.isSelected) {
                categoryCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                tvName.setTextColor(ContextCompat.getColor(context, R.color.primary))
            } else {
                categoryCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                tvName.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
            }

            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }
}
