package com.example.hvalevel4_task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hvalevel4_task2.R
import com.example.hvalevel4_task2.model.Games
import kotlinx.android.synthetic.main.history_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class ProductAdapter(private val Games: List<Games>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Games[position])
    }


    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return Games.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Games) {
            MainActivity.changeImage(game.computerPick,itemView.ivComputer)
            MainActivity.changeImage(game.computerPick,itemView.ivYou)
            MainActivity.changeStatus(game.status,itemView.tvStatus,itemView.context)
            itemView.tvDate.text =  Date(game.date).toString()

        }
    }
}