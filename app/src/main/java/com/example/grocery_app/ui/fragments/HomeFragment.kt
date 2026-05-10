package com.example.grocery_app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_app.R
import com.example.grocery_app.data.model.Product
import com.example.grocery_app.ui.activities.MainActivity
import com.example.grocery_app.ui.adapters.CategoryAdapter
import com.example.grocery_app.ui.adapters.ProductAdapter
import com.example.grocery_app.utils.TimeUtils
import com.example.grocery_app.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    private lateinit var rvCategories: RecyclerView
    private lateinit var rvProducts: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tvClearSearch: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvProductsTitle: TextView
    private lateinit var tvProductCount: TextView
    private lateinit var layoutEmpty: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initViewModel()
        setupRecyclerViews()
        setupSearch()
        observeData()
    }

    private fun initViews(view: View) {
        rvCategories = view.findViewById(R.id.rvCategories)
        rvProducts = view.findViewById(R.id.rvProducts)
        etSearch = view.findViewById(R.id.etSearch)
        tvClearSearch = view.findViewById(R.id.tvClearSearch)
        tvGreeting = view.findViewById(R.id.tvGreeting)
        tvProductsTitle = view.findViewById(R.id.tvProductsTitle)
        tvProductCount = view.findViewById(R.id.tvProductCount)
        layoutEmpty = view.findViewById(R.id.layoutEmpty)

        tvGreeting.text = TimeUtils.getGreeting()
    }

    private fun initViewModel() {
        viewModel = (requireActivity() as MainActivity).homeViewModel
    }

    private fun setupRecyclerViews() {
        // Categories horizontal list
        categoryAdapter = CategoryAdapter { category ->
            viewModel.selectCategory(category.name)
        }
        rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Products 2-column grid
        productAdapter = ProductAdapter(
            onAddClick = { product -> viewModel.addToCart(product) },
            onMinusClick = { product -> viewModel.removeFromCart(product) }
        )
        rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.search(query)
                tvClearSearch.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })

        tvClearSearch.setOnClickListener {
            etSearch.text.clear()
            viewModel.search("")
        }
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
            updateProductsHeader(products)

            layoutEmpty.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
            rvProducts.visibility = if (products.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            tvProductsTitle.text = if (category == "All") "All Products" else category
        }
    }

    private fun updateProductsHeader(products: List<Product>) {
        val count = products.size
        tvProductCount.text = "$count item${if (count != 1) "s" else ""}"
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshOnResume()
    }
}
