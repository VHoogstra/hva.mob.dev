package com.example.level3_task1

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val firstName: String,
    val lastName: String,
    val description: String,
    val imageUri: Uri?
) : Parcelable
