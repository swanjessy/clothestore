package com.example.clothestore.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart_item")
@Parcelize
data class CartItem(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val product_id: String,
    @ColumnInfo(name = "count")
    var count: Int = 1,
    @ColumnInfo(name = "product_price")
    val product_price: Double,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "image")
    val image: String
) : Parcelable