package com.example.danshotspotapp.database.Todo


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo order by date")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert
    fun insertTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Query("DELETE FROM todo where 1")
    fun deleteAll()

    @Insert
    fun insertAllTodo(todos: List<Todo>)

    @Query("SELECT * FROM todo WHERE event_id = :event_id")
    fun getAllTodoEvent(event_id: Long?):  List<Todo>

    @Query("SELECT * FROM todo WHERE event_id = :event_id and status = 0")
    fun getAllTodoEventNotDone(event_id: Long?):  List<Todo>
}
