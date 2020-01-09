package com.example.logicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.logica_activity.*


class MainLogicaActivity : AppCompatActivity() {

    private var status: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.logica_activity)
        btnSubmit.setOnClickListener {
            CheckAwnser()
        }
    }

    private fun CheckAwnser() {
        status = 0
        if ((etC4.text.toString().toUpperCase() != "F") ||
            (etC3.text.toString().toUpperCase() != "F") ||
            (etC1.text.toString().toUpperCase() != "T") ||
            (etC2.text.toString().toUpperCase() != "F")) {
            onAnswerIncorrect()
        } else {
            onAnswerCorrect()
        }
    }

    /**
     * Displays a successful Toast message.
     */
    private fun onAnswerCorrect() {
        Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
    }

    /**
     * Displays a incorrect Toast message.
     */
    private fun onAnswerIncorrect() {
        Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
    }
}
