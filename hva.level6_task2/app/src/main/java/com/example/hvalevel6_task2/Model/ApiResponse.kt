package com.example.hvalevel6_task2.Model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results")
    var movies: List<Movie>
)