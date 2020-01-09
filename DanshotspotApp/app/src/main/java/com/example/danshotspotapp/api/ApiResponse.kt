package com.example.danshotspotapp.api


import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Todo.Todo
import com.google.gson.annotations.SerializedName

data class ApiResponseEvents(@SerializedName("data") var data: List<Event>)
data class ApiResponseEvent(@SerializedName("data") var data: Event)
data class ApiResponseTodo(@SerializedName("data") var data: Todo)
data class ApiResponseTodos(@SerializedName("data") var data: List<Todo>)