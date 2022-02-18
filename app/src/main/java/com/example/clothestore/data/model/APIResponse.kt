package com.example.clothestore.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIResponse(
    val products: List<Product>
) : Parcelable