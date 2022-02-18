package com.example.clothestore.presentation.features.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clothestore.data.model.Product
import com.example.clothestore.data.util.Resource
import com.example.clothestore.databinding.FragmentCatalogueBinding
import com.example.clothestore.presentation.component.UiBottomSheet
import com.example.clothestore.presentation.features.basket.BasketViewModel
import com.example.clothestore.presentation.features.wishlist.WishListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CatalogueFragment : Fragment(), CatalogueAdapter.OnProductClickListener,
    UiBottomSheet.BottomSheetClickListener {
    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CatalogueViewModel by viewModels()
    private val viewModelBasket: BasketViewModel by viewModels()
    private val wishListViewModel: WishListViewModel by viewModels()
    private var uiBottomSheet: UiBottomSheet? = null

    @Inject
    lateinit var catalogueAdapter: CatalogueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogueBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getCatalogueResponseFlow()
            viewModel.productFlow.collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        catalogueAdapter.differ.submitList(response.data?.products?.toList())

                    }
                    is Resource.Error -> {
                        hideLoading()
                        response.message.let {
                            Toast.makeText(activity, "${it}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.productsRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            adapter = catalogueAdapter
        }
        catalogueAdapter.onProductClickListener = this
    }

    private fun showLoading() {
        binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
        binding.loaderLayout.circularLoader.showAnimationBehavior
        binding.productsRecyclerView.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loaderLayout.circularLoader.hideAnimationBehavior
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
        binding.productsRecyclerView.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showBottomSheet(product: Product) {
        uiBottomSheet = UiBottomSheet.newInstance(product)
        uiBottomSheet?.show(childFragmentManager, uiBottomSheet?.tag)
        uiBottomSheet?._listener = this

    }

    override fun onProductClick(product: Product) {
        showBottomSheet(product = product)
    }

    override fun onItemWishList(product: Product) {
        wishListViewModel.addToWishlist(product)
        Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show()
    }

    override fun onItemAddToCart(product: Product) {
        viewModelBasket.addToBasket(product)
        Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show()
    }

}