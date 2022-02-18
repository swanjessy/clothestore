package com.example.clothestore.presentation.features.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothestore.data.model.CartItem
import com.example.clothestore.databinding.ItemBasketBinding
import javax.inject.Inject

class BasketAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ItemBasketBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val cartItem = differ.currentList[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class BasketViewHolder(
        private val binding: ItemBasketBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.productNameTv.text = cartItem.product_name
            binding.productPrice.text = "$${cartItem.product_price}"
            binding.productQuantity.text = "Qty:${cartItem.count}"

            Glide.with(binding.productImageView.context).load(cartItem.image)
                .circleCrop()
                .into(binding.productImageView)

        }
    }


}









