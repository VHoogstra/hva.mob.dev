package com.example.danshotspotapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.danshotspotapp.R
import com.example.danshotspotapp.Util
import com.example.danshotspotapp.adapter.TodoAdapter
import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Todo.Todo
import com.example.danshotspotapp.database.Todo.TodoRepository
import com.example.danshotspotapp.model.TodoEventViewModel
import kotlinx.android.synthetic.main.activity_event_show.*
import java.util.*


class EventShowActivity : AppCompatActivity() {
    private val todos = arrayListOf<Todo>()
    private val todoAdapter = TodoAdapter(todos, { todo -> onTodoClick(todo) })
    private lateinit var viewModel: TodoEventViewModel
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        event = intent.getParcelableExtra<Event>(EVENT_OBJECT)

        super.onCreate(savedInstanceState)

        val todosDb = TodoRepository(application.applicationContext).getAllTodoEvent(event)
        this.todos.clear()
        this.todos.addAll(todosDb)

        setContentView(R.layout.activity_event_show)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvTitle.setText(getString(R.string.title) + ": " + event.title)
        tvLocation.setText(getString(R.string.location) + ": " + event.location)
        tvDateShow.setText(Util.getDate(event.date, "d-M-Y kk:mm"))
        updateCounter()
        initViews()
        initViewModel()

    }
    fun updateCounter(){
        val todosDbDone =
            TodoRepository(application.applicationContext).getAllTodoEventNotDone(event)
        tvTodo.setText((todos.size - todosDbDone.size).toString() + "/" + todos.size)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TodoEventViewModel::class.java)
        // Observe reminders from the view model, update the list when the data is changed.
        viewModel.todos.observe(this, Observer { events ->
            this.todos.clear()
            this.todos.addAll(events)
            todoAdapter.notifyDataSetChanged()
            updateCounter()
        })
        viewModel.loading.observe(this, Observer {
            updateCounter()

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
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
        viewModel.updateTodo(todo)
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
            android.R.id.home -> {
                finish()
                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.slideup
                )

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvEventTodo.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvEventTodo.adapter = todoAdapter
//        rvTodo.addItemDecoration(
//            DividerItemDecoration(
//                this@TodoActivity,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        createItemTouchHelper().attachToRecyclerView(rvEventTodo)
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
