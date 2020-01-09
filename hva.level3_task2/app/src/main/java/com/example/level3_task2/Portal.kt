package com.example.level3_task2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Portal(
    val title: String,
    val url: String
) : Parcelable