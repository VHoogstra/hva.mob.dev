package com.example.danshotspotapp.database.Todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.danshotspotapp.database.Event.eventRoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDao(): TodoDao

    companion object {
        private const val DATABASE_NAME = "todo"

        @Volatile
        private var reminderRoomDatabaseInstance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase? {
            if (reminderRoomDatabaseInstance == null) {
                synchronized(eventRoomDatabase::class.java) {
                    if (reminderRoomDatabaseInstance == null) {
                        reminderRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            TodoDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return reminderRoomDatabaseInstance
        }
    }


}