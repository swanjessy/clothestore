package com.example.clothestore.presentation.features.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clothestore.data.model.Product
import com.example.clothestore.databinding.FragmentWishListBinding
import com.example.clothestore.presentation.features.basket.BasketViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListFragment : Fragment(), WishlistAdapter.OnProductClickListener {
    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WishListViewModel by viewModels()
    private val viewModelBasket: BasketViewModel by viewModels()

    @Inject
    lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getFavouriteProducts()
        viewModel.favItems.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                wishlistAdapter.differ.submitList(it)
                binding.emptyStateMessage.visibility = View.GONE
                binding.wishListRecyclerView.visibility = View.VISIBLE
            } else {
                binding.wishListRecyclerView.visibility = View.GONE
                binding.emptyStateMessage.visibility = View.VISIBLE
            }
        })
    }

    private fun initRecyclerView() {
        binding.wishListRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            adapter = wishlistAdapter
        }
        wishlistAdapter.onProductClickListener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAddToCartClick(product: Product) {
        viewModelBasket.addToBasket(product)
        viewModel.updateWishList(product)

    }

    override fun onDeleteFromWishlist(product: Product) {
        Snackbar.make(
            binding.root,
            "Do you really want to delete from wish list?",
            Snackbar.LENGTH_LONG
        ).apply {
            setAction("Yes") {
                viewModel.updateWishList(product)
            }
            show()
        }


    }

}