package com.example.hvalevel6_task2.Api


class MoviesRepository {

    private val moviesApiService: MoviesApiService = MoviesApi.createApi()

    fun getMoviesByYear(year: String) = moviesApiService.getMoviesByYear(year)

}