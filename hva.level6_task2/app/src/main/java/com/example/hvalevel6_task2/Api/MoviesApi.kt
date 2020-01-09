package com.example.hvalevel6_task2.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesApi {

    companion object {
        private const val baseUrl = "https://api.themoviedb.org/3/"

        fun createApi(): MoviesApiService {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
            val moviesApi = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

            return moviesApi.create(MoviesApiService::class.java)

        }

    }

}
