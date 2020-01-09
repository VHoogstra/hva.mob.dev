package com.example.danshotspotapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.danshotspotapp.R
import com.example.danshotspotapp.adapter.EventsAdapter
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.activity_events.menu_todo
import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.model.*


const val GET_NEW_EVENT = 300

class EventActivity : AppCompatActivity() {
    private val events = arrayListOf<Event>()
    private val eventAdapter = EventsAdapter(events, { event -> onEventClick(event) })
    private lateinit var viewModelEvent: EventActivityViewModel
    private lateinit var viewModelTodo: TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        fab.setOnClickListener { view ->
            val intent = Intent(this, EventCreateActivity::class.java)
            startActivityForResult(intent, GET_NEW_EVENT)
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fadeout
            )
        }

        menu_todo.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slideright,
                R.anim.fadeout
            )
            finish()
        }
        initViewModel()
        initViews()
        sync()
        scEvent
        scEvent.setOnRefreshListener  {
            sync()
        }
        // Configure the refreshing colors
        scEvent.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_open_settings -> {
                val profileActivityIntent = Intent(this, SettingsActivity::class.java)
                startActivity(profileActivityIntent)
                overridePendingTransition(
                    R.anim.slidedown,
                    R.anim.fadeout
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GET_NEW_EVENT -> {
                    val event = data!!.getParcelableExtra<Event>(EVENT_OBJECT)
                    viewModelEvent.insertEvent(event)
                }
            }
        }
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvEvent.layoutManager =
            LinearLayoutManager(this@EventActivity, RecyclerView.VERTICAL, false)
        rvEvent.adapter = eventAdapter
        rvEvent.addItemDecoration(
            DividerItemDecoration(
                this@EventActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(rvEvent)
    }

    private fun initViewModel() {
        viewModelEvent = ViewModelProviders.of(this).get(EventActivityViewModel::class.java)
        viewModelTodo = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        // Observe reminders from the view model, update the list when the data is changed.
        viewModelEvent.events.observe(this, Observer { events ->
            this@EventActivity.events.clear()
            this@EventActivity.events.addAll(events)
            eventAdapter.notifyDataSetChanged()
        })
        viewModelEvent.loading.observe(this, Observer {
            scEvent.isRefreshing = it != false
        })

    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
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
//                val position = viewHolder.adapterPosition
//                reminders.removeAt(position)
//                reminderAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun sync() {
        viewModelEvent.updateFromDHS()
        viewModelTodo.updateFromDHS()
    }

    private fun onEventClick(event: Event) {
        val activity = this as Activity
        val showIntent = Intent(this, EventShowActivity::class.java)
        showIntent.putExtra(EVENT_OBJECT, event)
        activity.startActivity(showIntent)
        activity.overridePendingTransition(
            R.anim.slidedown,
            R.anim.fadeout
        )
    }

    /**
     * reload data when reactive
     */
    public override fun onStart() {
        super.onStart()
        val vmTodo = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        vmTodo.updateFromDHS()
        eventAdapter.notifyDataSetChanged()

    }


}
