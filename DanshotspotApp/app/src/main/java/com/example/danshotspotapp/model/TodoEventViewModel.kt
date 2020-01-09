package com.example.danshotspotapp.model


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.danshotspotapp.api.ApiResponseTodo
import com.example.danshotspotapp.api.ApiResponseTodos
import com.example.danshotspotapp.api.DansRepository
import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Todo.Todo
import com.example.danshotspotapp.database.Todo.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoEventViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val todoRepository =
        TodoRepository(application.applicationContext)

    val todos = MutableLiveData<List<Todo>>()
    val todosDone = MutableLiveData<List<Todo>>()
    val loading = MutableLiveData<Boolean>(false)

    fun init(event: Event){
        this.todos.value = todoRepository.getAllTodoEvent(event)
    }
    fun insertTodo(todo: Todo) {
        ioScope.launch {
            todoRepository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        loading.value = true
        val apiInterface = DansRepository()
        val call = apiInterface.updateTodo(todo)
        this.onResponseTodo(call)


        ioScope.launch {
            todoRepository.updateTodo(todo)
        }

    }

    fun replaceAllTasks(todos: List<Todo>) {
        ioScope.launch {
            todoRepository.deleteAll()
            todoRepository.insertAllTodo(todos)
        }
    }

    fun deleteAll() {
        ioScope.launch {
            todoRepository.deleteAll()
        }
    }

    fun insertAllTodo(todos: List<Todo>) {
        ioScope.launch {
            todoRepository.insertAllTodo(todos)
        }
    }

    fun updateFromDHS() {
        loading.value = true
        val apiInterface = DansRepository()
        val call = apiInterface.getAllTodo()
        this.onResponseTodos(call)

    }

    private fun onResponseTodo(response: Call<ApiResponseTodo>) {
        response.enqueue(object : Callback<ApiResponseTodo> {
            override fun onResponse(
                call: Call<ApiResponseTodo>,
                response: Response<ApiResponseTodo>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    println("done inserting todo")
                } else {
                    println("failed")
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ApiResponseTodo>, t: Throwable) {
                println("how u get here..")
                loading.value = false
            }
        })
    }

    private fun onResponseTodos(response: Call<ApiResponseTodos>) {
        response.enqueue(object : Callback<ApiResponseTodos> {
            override fun onResponse(
                call: Call<ApiResponseTodos>,
                response: Response<ApiResponseTodos>
            ) {
                if (response.isSuccessful) {
                    replaceAllTasks(response.body()!!.data)
                    loading.value = false
                    println("done inserting todo")
//todo stop spinner
                } else {
                    println("failed")
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ApiResponseTodos>, t: Throwable) {
                println("how u get here..")
                loading.value = false
            }
        })
    }
}
