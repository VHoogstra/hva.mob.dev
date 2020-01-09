package com.example.hvalevel4_task2.model

import androidx.room.*


@Entity(tableName = "games_table")

class Games(
    @ColumnInfo(name = "computerPick")
    var computerPick: Int,
    @ColumnInfo(name = "userPick")
    var userPick: Int,
    @ColumnInfo(name = "status")
    var status: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "date")
    var date: Long
)


