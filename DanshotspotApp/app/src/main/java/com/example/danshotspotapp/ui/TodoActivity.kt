package com.example.danshotspotapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.danshotspotapp.R
import com.example.danshotspotapp.adapter.TodoAdapter
import com.example.danshotspotapp.database.Todo.Todo
import com.example.danshotspotapp.model.EventActivityViewModel
import com.example.danshotspotapp.model.TodoViewModel
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.activity_todo.menu_event
import java.util.*





class TodoActivity : AppCompatActivity() {
    private val todos = arrayListOf<Todo>()
    private val todoAdapter = TodoAdapter(todos, { todo -> onTodoClick(todo) })
    private lateinit var viewModelEvent: EventActivityViewModel
    private lateinit var viewModelTodo: TodoViewModel

    private var swipeContainer: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        menu_event.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slideleft,
                R.anim.fadeout
            )
            finish()
        }
        menu_todo.setOnClickListener {

        }
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val name = sharedPreferences.getString("username", "test")
        menu_event.setText(name)
        scTodo.setOnRefreshListener  {

            sync()
        }
        // Configure the refreshing colors
        scTodo.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        initViewModel()
        initViews()
        sync()

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

    private fun sync() {
        viewModelEvent.updateFromDHS()
        viewModelTodo.updateFromDHS()
    }

    private fun initViewModel() {
        viewModelEvent = ViewModelProviders.of(this).get(EventActivityViewModel::class.java)
        viewModelTodo = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        // Observe reminders from the view model, update the list when the data is changed.
        viewModelTodo.todos.observe(this, Observer { events ->
            this@TodoActivity.todos.clear()
            this@TodoActivity.todos.addAll(events)
            todoAdapter.notifyDataSetChanged()
        })
        viewModelTodo.loading.observe(this, Observer {
            scTodo.isRefreshing = it != false
        })
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvTodo.layoutManager =
            LinearLayoutManager(this@TodoActivity, RecyclerView.VERTICAL, false)
        rvTodo.adapter = todoAdapter
//        rvTodo.addItemDecoration(
//            DividerItemDecoration(
//                this@TodoActivity,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        createItemTouchHelper().attachToRecyclerView(rvTodo)
    }

    private fun onTodoClick(todo: Todo) {
        println("saving " + todo.title)

        val calendar = GregorianCalendar(
        )
        val time = calendar.getTimeInMillis()
        if (todo.status == 1) {
            todo.status = 0
            todo.status_done = 0
        } else {

            todo.status = 1
            todo.status_done = time
        }

        viewModelTodo.updateTodo(todo)
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

}
