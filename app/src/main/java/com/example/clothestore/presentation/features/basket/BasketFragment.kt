package com.example.clothestore.presentation.features.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothestore.databinding.FragmentBasketBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BasketViewModel by viewModels()

    @Inject
    lateinit var basketAdapter: BasketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val cartItem = basketAdapter.differ.currentList[position]
                viewModel.deleteFromBasket(cartItem)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).show()
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.basketRecyclerView)
        }
    }

    private fun observeViewModel() {
        viewModel.getBasketItems()
        viewModel.cartItems.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                basketAdapter.differ.submitList(it)
                binding.emptyStateMessage.visibility = View.GONE
                binding.basketRecyclerView.visibility = View.VISIBLE
            } else {
                binding.basketRecyclerView.visibility = View.GONE
                binding.emptyStateMessage.visibility = View.VISIBLE
            }
            viewModel.getItemsPriceTotal()
        })
        viewModel.totalAmount.observe(viewLifecycleOwner, {
            binding.totalAmount.text = "$$it"
        })
    }

    private fun initRecyclerView() {
        binding.basketRecyclerView.apply {
            adapter = basketAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}