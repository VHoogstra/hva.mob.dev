package com.example.danshotspotapp.api

import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Todo.Todo

class DansRepository {
    private val dhsApi: DanshotspotApiService = DanshotspotApi.createApi()

    fun getAllEvents() = dhsApi.getAllEvents()
    fun getAllTodo() = dhsApi.getAllTodo()
    fun updateTodo(todo: Todo) = dhsApi.updateTodo(todo.dhs_id, todo.status_done, todo.status)

    fun getEvent() = dhsApi.getEvent()
    fun CreateEvent(event: Event) = dhsApi.CreateEvent(event.title, event.date, event.location)
}
