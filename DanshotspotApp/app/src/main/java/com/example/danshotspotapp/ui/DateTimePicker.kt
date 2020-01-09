package com.example.danshotspotapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.date_time_picker.*
import android.widget.TimePicker
import android.widget.DatePicker
import com.example.danshotspotapp.R
import java.util.*


const val TIME_DATA = "TIME_DATA"
class DateTimePicker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_time_picker)

        date_time_set.setOnClickListener{
            val datePicker = this.findViewById(R.id.date_picker) as DatePicker
            val timePicker = this.findViewById(R.id.time_picker) as TimePicker

            val calendar = GregorianCalendar(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth,
                timePicker.currentHour,
                timePicker.currentMinute
            )
            val time = calendar.getTimeInMillis()
            System.out.println(time)

            val resultIntent = Intent()
            resultIntent.putExtra(TIME_DATA, time.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fadeout
            )
        }
    }


}