package com.example.danshotspotapp.database.Todo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "todo")
data class Todo(
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String,

    @SerializedName("note")
    @ColumnInfo(name = "note")
    var note: String,

    @SerializedName("date")
    @ColumnInfo(name = "date")
    var date: Long,

    @SerializedName("status")
    @ColumnInfo(name = "status")
    var status: Int,

    @SerializedName("status_done")
    @ColumnInfo(name = "status_done")
    var status_done: Long,

    @SerializedName("event")
    @ColumnInfo(name = "event_id")
    var event_id: Long?,

    @SerializedName("id")
    @ColumnInfo(name = "dhs_id")
    var dhs_id: Long? = null,

    @SerializedName("id_s")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable