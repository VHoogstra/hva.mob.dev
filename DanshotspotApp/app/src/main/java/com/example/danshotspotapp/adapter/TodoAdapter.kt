package com.example.danshotspotapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.danshotspotapp.R
import com.example.danshotspotapp.Util
import com.example.danshotspotapp.database.Todo.Todo
import kotlinx.android.synthetic.main.todo_item.view.*


class TodoAdapter(private val Todos: List<Todo>, private val onClick: (Todo) -> Unit) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Todos[position])
    }


    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return Todos.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context

        init {
            itemView.todo_check.setOnClickListener { onClick(Todos[adapterPosition]) }
        }

        fun bind(todo: Todo) {
            if (todo.status == 1) {
                itemView.todo_check.isChecked = true
                println(todo.title + "has been checked")
            }else{
                itemView.todo_check.isChecked= false
            }
            println(todo.status.toString() + " todo = checker "+ itemView.todo_check.isChecked)

//            itemView.mdCard.setChecked(true)
//            itemView.todo_check.isChecked = event.status == 0
            itemView.tvTitle.text = todo.title + " | " +todo.event_id
//            itemView.tvDate.text = todo.status.toString()
            itemView.tvDate.text = Util.getDate(todo.date, "d-M-Y kk:mm")
        }
    }
}