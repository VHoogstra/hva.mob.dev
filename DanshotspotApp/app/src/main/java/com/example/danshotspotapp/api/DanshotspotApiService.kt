package com.example.danshotspotapp.api

import androidx.room.Update
import com.example.danshotspotapp.database.Event.Event
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded


public interface DanshotspotApiService {
    // The GET method needed to retrieve a random number trivia.
    @GET("/api/event/all?api_token=sdfsdfsdfsdf")
    fun getAllEvents(): Call<ApiResponseEvents>

    @GET("/api/todo/all?api_token=sdfsdfsdfsdf")
    fun getAllTodo(): Call<ApiResponseTodos>

    @GET("event/get?api_token=sdfsdfsdfsdf")
    fun getEvent(): Call<ApiResponseEvent>

    @POST("event?api_token=sdfsdfsdfsdf")
    fun CreateEvenddt(event: Event): Call<ApiResponseEvent>

    @FormUrlEncoded
    @POST("event?api_token=sdfsdfsdfsdf")
    fun CreateEvent(@Field("name") name: String, @Field("date") date: Long, @Field("location") location: String): Call<ApiResponseEvent>

    @FormUrlEncoded
    @POST("todo/update?api_token=sdfsdfsdfsdf")
    fun updateTodo(@Field("id") id: Long?, @Field("status_done") status_done: Long, @Field("status") status: Int): Call<ApiResponseTodo>
}
