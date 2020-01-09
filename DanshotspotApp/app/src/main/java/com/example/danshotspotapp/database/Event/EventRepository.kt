package com.example.danshotspotapp.database.Event

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch

public class EventRepository(context: Context) {

    private var eventDao: EventDao

    init {
        val eventRoomDatabase =
            eventRoomDatabase.getDatabase(
                context
            )
        eventDao = eventRoomDatabase!!.eventDao()
    }

    fun getAllEvents(): LiveData<List<Event>> {
        return eventDao.getAllEvents()
    }


    fun insertEvent(reminder: Event) {
        eventDao.insertEvent(reminder)
    }

    fun delteEvent(reminder: Event) {
        eventDao.delteEvent(reminder)
    }

    fun updateEvent(reminder: Event) {
        eventDao.updateEvent(reminder)
    }

    fun deleteAll() {
        eventDao.deleteAll()
    }
    fun insertAllEvents(events: List<Event>) {
        eventDao.insertAllEvents(events)
    }
}
