package com.example.clothestore.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "oldPrice")
    val oldPrice: Double,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "product_id")
    @PrimaryKey
    val productId: String,
    @ColumnInfo(name = "stock")
    val stock: Int
) : Parcelable {
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
}