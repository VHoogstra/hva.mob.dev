package com.example.hvalevel4_task2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hvalevel4_task2.R
import com.example.hvalevel4_task2.database.GamesRepository
import com.example.hvalevel4_task2.model.Games
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val games = arrayListOf<Games>()
    private val gamesAdapter = ProductAdapter(games)
    private lateinit var gamesRepository: GamesRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =  this.getString(R.string.gameHistory)
        gamesRepository = GamesRepository(this)
        initViews()
    }

    /**
     * process menu items
     */

    /**
     * create menu shown
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    private fun initViews() {
        rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gamesAdapter
        rvHistory.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(rvHistory)
        getGamesFromDatabase()
    }

    private fun deleteGameHistory(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gamesRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }
    }

    private fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val shoppingList = withContext(Dispatchers.IO) {
                gamesRepository.getAllGames()
            }
            games.clear()
            games.addAll(shoppingList)
            gamesAdapter.notifyDataSetChanged()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        gamesRepository.deleteGames(gameToDelete)
                    }
                    getGamesFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}
