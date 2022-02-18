package com.example.clothestore.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.clothestore.R
import com.example.clothestore.databinding.ActivityMainBinding
import com.example.clothestore.presentation.component.setBadge
import com.example.clothestore.presentation.features.basket.BasketViewModel
import com.example.clothestore.presentation.features.wishlist.WishListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var navController: NavController
    private val viewModelBasket: BasketViewModel by viewModels()
    private val wishListViewModel: WishListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding?.navBottom?.setupWithNavController(navController)

        setUpObservers()

    }

    private fun setUpObservers() {
        viewModelBasket.getBasketItems()
        viewModelBasket.cartItems.observe(this, { favourites ->
            var totalCount = 0
            favourites.forEach {
                totalCount += it.count
            }
            binding?.navBottom?.setBadge(R.id.basket, totalCount)
        })
        wishListViewModel.getFavouriteProducts()
        wishListViewModel.favItems.observe(this, { favourites ->
            binding?.navBottom?.setBadge(R.id.wishList, favourites.size)
        })
    }

}