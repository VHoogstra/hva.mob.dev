package com.example.danshotspotapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.danshotspotapp.R
import com.example.danshotspotapp.Util
import com.example.danshotspotapp.database.Event.Event
import com.example.danshotspotapp.database.Todo.TodoRepository
import kotlinx.android.synthetic.main.event_item.view.*


class EventsAdapter(private val Events: List<Event>, private val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Events[position])
    }


    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.event_item,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return Events.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context

        init {
            itemView.setOnClickListener { onClick(Events[adapterPosition]) }
        }
        fun bind(event: Event) {
            val  todoNotDone = TodoRepository(itemView.context).getAllTodoEventNotDone(event).size
            val todos =TodoRepository(itemView.context).getAllTodoEvent(event).size

            itemView.tvDateShow.text = event.title
            itemView.tvEvent.text = Util.getDate(event.date, "d-M-Y kk:mm")
            itemView.tvTodo.text = (todos-todoNotDone).toString() + "/" +todos
        }
    }
}