package com.example.danshotspotapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val AUTH_HEADER = "sdfsdfsdfsdf"

class DanshotspotApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "http:/192.168.0.105/api/"

        /**
         * @return [DanshotspotApiService] The service class off the retrofit client.
         */
        fun createApi(): DanshotspotApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val numbersApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit DanshotspotApiService
            return numbersApi.create(DanshotspotApiService::class.java)
        }
    }
}
