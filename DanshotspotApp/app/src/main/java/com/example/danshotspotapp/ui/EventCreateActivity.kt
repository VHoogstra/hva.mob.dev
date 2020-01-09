package com.example.danshotspotapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.danshotspotapp.R
import com.example.danshotspotapp.Util
import com.example.danshotspotapp.database.Event.Event
import kotlinx.android.synthetic.main.activity_event_create.*

const val GET_DATE_PICKER_RESULT = 100
const val EVENT_OBJECT = "EVENT_OBJECT"

class EventCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_create)

        btnDate.setOnClickListener {
            val intent = Intent(this, DateTimePicker::class.java)
            startActivityForResult(intent, GET_DATE_PICKER_RESULT)
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fadeout
            )
        }
        btnSave.setOnClickListener {
            onSaveClick()
        }
    }

    fun onSaveClick() {
        if (etTitle.text.toString().isNotBlank() && tvDate.text.toString().isNotBlank() && etLocation.text.toString().isNotBlank()) {
            val event = Event(
                etTitle.text.toString(),
                tvDate.text.toString().toLong(),
                etLocation.text.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EVENT_OBJECT, event)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(
                this, "The Event cannot be empty"
                , Toast.LENGTH_SHORT
            ).show()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GET_DATE_PICKER_RESULT -> {
                    val dateMili = data!!.getStringExtra(TIME_DATA)
                    tvDateShow.setText(Util.getDate(dateMili.toLong()/1000,"d-M-Y kk:mm"))
                    tvDate.setText(dateMili)
                }
            }
        }
    }


}
