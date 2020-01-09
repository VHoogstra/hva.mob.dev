package com.example.level3_task2

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_portal.view.*

const val HTTP_STRING = "http"

class PortalAdapter(private val portals: List<Portal>) :
    RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portals[position])
        //add event listener to the item
        holder.itemView.setOnClickListener {
            //get the clicked portal
            val portal = portals[holder.layoutPosition]
            //setup the intent to open chrome
            val builder = CustomTabsIntent.Builder()
            var color = ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary);
            builder.setToolbarColor(color)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(holder.itemView.context, Uri.parse(portal.url))

        }
    }


    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_portal, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return portals.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(portal: Portal) {
            itemView.tvTitle.text = portal.title
            itemView.tvUrl.text = portal.url
        }

    }
}