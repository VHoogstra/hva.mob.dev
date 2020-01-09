package com.example.danshotspotapp.database.Todo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.danshotspotapp.database.Event.Event

public class TodoRepository(context: Context) {

    private var todoDao: TodoDao

    init {
        val todoDatabase =
            TodoDatabase.getDatabase(context)
        todoDao = todoDatabase!!.todoDao()
    }

    fun getAllTodo(): LiveData<List<Todo>> {
        return todoDao.getAllTodo()
    }
    fun getAllTodoEvent(event: Event): List<Todo> {
        return todoDao.getAllTodoEvent(event.dhs_id)
    }
    fun getAllTodoEventNotDone(event: Event): List<Todo> {
        return todoDao.getAllTodoEventNotDone(event.dhs_id)
    }

    fun insertTodo(todo: Todo) {
        return todoDao.insertTodo(todo)
    }

    fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    fun deleteAll() {
        todoDao.deleteAll()
    }

    fun insertAllTodo(todos: List<Todo>){
        todoDao.insertAllTodo(todos)
    }
}
