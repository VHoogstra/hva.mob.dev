package com.example.danshotspotapp

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.danshotspotapp.model.EventActivityViewModel
import com.example.danshotspotapp.model.TodoViewModel
import com.example.danshotspotapp.ui.TodoActivity
import java.text.SimpleDateFormat
import java.util.*

object Util {
    private lateinit var vmEvent: EventActivityViewModel
    private lateinit var vmTodo: TodoViewModel

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds * 1000)
        return formatter.format(calendar.getTime())
    }
}