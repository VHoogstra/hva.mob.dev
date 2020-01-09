package com.example.danshotspotapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.danshotspotapp.api.ApiResponseEvent
import com.example.danshotspotapp.api.ApiResponseEvents
import com.example.danshotspotapp.api.DansRepository
import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Event.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val eventRepository =
        EventRepository(application.applicationContext)
    val apiInterface = DansRepository()

    val events: LiveData<List<Event>> = eventRepository.getAllEvents()
    val loading = MutableLiveData<Boolean>(false)

    fun insertEvent(event: Event) {
        loading.value = true
        val call = apiInterface.CreateEvent(event)
        this.onResponseEvent(call)
        ioScope.launch {
            eventRepository.insertEvent(event)
        }
    }

    fun deleteEvent(event: Event) {
        ioScope.launch {
            eventRepository.delteEvent(event)
        }

    }
    fun replaceAllTasks(event: List<Event> ){
        ioScope.launch {
            eventRepository.deleteAll()
            eventRepository.insertAllEvents(event)
        }
    }

    fun updateFromDHS() {
        loading.value = true
        val call = apiInterface.getAllEvents()
        this.onResponseEvents(call)
    }

    private fun onResponseEvents(response: Call<ApiResponseEvents>) {
        response.enqueue(object : Callback<ApiResponseEvents> {
            override fun onResponse(
                call: Call<ApiResponseEvents>,
                response: Response<ApiResponseEvents>
            ) {
                if (response.isSuccessful) {
                    replaceAllTasks(response.body()!!.data)
                    loading.value = false

                } else {
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ApiResponseEvents>, t: Throwable) {
                loading.value = false
            }
        })
    }
    private fun onResponseEvent(response: Call<ApiResponseEvent>) {
        response.enqueue(object : Callback<ApiResponseEvent> {
            override fun onResponse(
                call: Call<ApiResponseEvent>,
                response: Response<ApiResponseEvent>
            ) {
                if (response.isSuccessful) {
                    loading.value = false

                } else {
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ApiResponseEvent>, t: Throwable) {
                loading.value = false
            }
        })
    }
}
