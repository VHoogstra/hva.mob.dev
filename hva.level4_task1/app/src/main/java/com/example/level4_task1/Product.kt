package com.example.level4_task1

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "product_table")

class Product(
    @ColumnInfo(name = "name")
    var name: String,


    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)