package com.example.level4_task1



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_main.view.*

class ProductAdapter(private val reminders: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reminders[position])
    }


    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                android.R.layout.product,
                parent,
                false
            )
        )*/
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_main, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return reminders.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.tvQuintity.text = product.quantity.toString()
            itemView.tvName.text = product.name
        }

    }
}