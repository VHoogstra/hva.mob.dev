package com.example.level4_demo

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "reminderTable")
@Parcelize
data class Reminder(
    @ColumnInfo(name = "reminder")
    var reminder: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable
