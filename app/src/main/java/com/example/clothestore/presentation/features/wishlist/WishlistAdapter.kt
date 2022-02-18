package com.example.clothestore.presentation.features.wishlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothestore.data.model.Product
import com.example.clothestore.databinding.ItemWishListBinding
import javax.inject.Inject

class WishlistAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<WishlistAdapter.WishListViewHolder>() {

    var onProductClickListener: OnProductClickListener? = null

    private val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val binding = ItemWishListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return WishListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class WishListViewHolder(
        private val binding: ItemWishListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productNameTv.text = product.name
            binding.productPrice.text = "$${product.price}"

            Glide.with(binding.productImageView.context).load(product.image)
                .centerCrop()
                .into(binding.productImageView)

            binding.btnAddToCart.setOnClickListener {
                onProductClickListener?.onAddToCartClick(product)
            }

            binding.btnDelete.setOnClickListener {
                onProductClickListener?.onDeleteFromWishlist(product)
            }


        }
    }

    interface OnProductClickListener {
        fun onAddToCartClick(product: Product)
        fun onDeleteFromWishlist(product: Product)
    }


}









