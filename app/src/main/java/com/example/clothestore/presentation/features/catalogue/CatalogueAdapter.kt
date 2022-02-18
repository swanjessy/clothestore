package com.example.clothestore.presentation.features.catalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothestore.data.model.Product
import com.example.clothestore.databinding.CatalogueListItemBinding
import com.example.clothestore.presentation.Utils.implementSpringAnimationTrait
import javax.inject.Inject

class CatalogueAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<CatalogueAdapter.CatalogueViewHolder>() {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val binding = CatalogueListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogueViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CatalogueViewHolder(
        private val binding: CatalogueListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productNameTv.text = product.name
            binding.productPriceTv.text = "$${product.price}"

            Glide.with(binding.productImageView.context).load(product.image)
                .into(binding.productImageView)

            binding.root.implementSpringAnimationTrait()

            binding.root.setOnClickListener {
                onProductClickListener?.onProductClick(product)
            }

        }
    }

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }


}









