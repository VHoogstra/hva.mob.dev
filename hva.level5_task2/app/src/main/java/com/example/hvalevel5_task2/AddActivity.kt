package com.example.hvalevel5_task2

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.hvalevel5_task2.database.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val EXTRA_GAME = "EXTRA_GAME"

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            if (
                tvTitle.text != null &&
                tvDay.text != null &&
                tvMonth.text != null &&
                tvPlatform.text != null &&
                tvYear.text != null
            ) {
                val date = GregorianCalendar(
                    tvYear.text.toString().toInt(),
                    tvMonth.text.toString().toInt()-1,
                    tvDay.text.toString().toInt()
                )
                val game = Game(
                    tvTitle.text.toString(),
                    date.getTimeInMillis(),
                    tvPlatform.text.toString()
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_GAME, game)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(view, "pls fill all fields", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

}
