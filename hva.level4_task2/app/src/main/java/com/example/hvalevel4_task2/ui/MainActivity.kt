package com.example.hvalevel4_task2.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.hvalevel4_task2.R
import com.example.hvalevel4_task2.database.GamesRepository
import com.example.hvalevel4_task2.model.Games
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ROCK = 0
const val PAPER = 1
const val SCISSORS = 2
const val WIN = 2
const val LOSE = 1
const val DRAW = 0

class MainActivity : AppCompatActivity() {
    private lateinit var GamesRepository: GamesRepository
    private lateinit var context: Context
    var win = 0
    var draw = 0
    var lost = 0
    val WIN_ARRAY: Array<Array<Int>> = arrayOf(
        arrayOf(//rock [0]
            DRAW, //rock rock
            LOSE, //rock paper
            WIN //rock scissors
        ),
        arrayOf(//paper
            WIN, //paper rock
            DRAW, //paper paper
            LOSE //paper scissors
        ),
        arrayOf(//scissors
            LOSE, //scissors rock
            WIN, //scissors paper
            DRAW //scissors scissors
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GamesRepository = GamesRepository(this)
        context = this
        ivRock.setOnClickListener {
            choose(ROCK)
        }
        ivPaper.setOnClickListener {
            choose(PAPER)
        }
        ivScissors.setOnClickListener {
            choose(SCISSORS)
        }
        updateStatistic()
        initView()
    }

    fun initView() {

        this@MainActivity.runOnUiThread(java.lang.Runnable {
            tvStatistics.text = context.getString(R.string.statistics, win, draw, lost)
        })

    }

    fun updateStatistic() {

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                win = GamesRepository.getCountStatus(WIN)
                draw = GamesRepository.getCountStatus(DRAW)
                lost = GamesRepository.getCountStatus(LOSE)
                initView()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_show_history -> {
                val profileActivityIntent = Intent(this, HistoryActivity::class.java)
                    startActivity(profileActivityIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    fun choose(selection: Int) {

        var computer = computerDecides()
        var winner = whoWon(computer, selection)

        addGame(computer, selection, winner)

        //update screen
        changeStatus(winner, tvStatus, this)
        changeImage(selection, ivUser)
        changeImage(computer, ivComputer)
        updateStatistic()
    }

    fun computerDecides(): Int {
        return (0..2).random()
    }

    companion object {
        fun changeImage(selected: Int, imageView: ImageView) {
            if (selected == ROCK) {
                imageView.setImageResource(R.drawable.rock)
            }
            if (selected == PAPER) {
                imageView.setImageResource(R.drawable.paper)
            }
            if (selected == SCISSORS) {
                imageView.setImageResource(R.drawable.scissors)
            }
        }

        fun changeStatus(status: Int, textView: TextView, context: Context) {
            var StatusMessage = ""
            when (status) {
                DRAW -> StatusMessage = context.getString(R.string.youTruce)
                LOSE -> StatusMessage = context.getString(R.string.youLose)
                WIN -> StatusMessage = context.getString(R.string.youWin)
            }
            textView.text = StatusMessage
        }
    }

    fun whoWon(computer: Int, user: Int): Int {
        return WIN_ARRAY[user][computer]

    }

    private fun addGame(computer: Int, user: Int, status: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val Games = Games(
                computerPick = computer,
                userPick = user,
                status = status,
                date = System.currentTimeMillis()
            )

            withContext(Dispatchers.IO) {
                GamesRepository.insertGames(Games)
            }
        }
    }
}
