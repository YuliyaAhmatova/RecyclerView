package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val things: MutableList<Thing>) :
    RecyclerView.Adapter<CustomAdapter.ThingViewHolder>() {

    class ThingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)
        val descriptionTV: TextView = itemView.findViewById(R.id.descriptionTV)
        val imageViewIV: ImageView = itemView.findViewById(R.id.imageViewIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ThingViewHolder(itemView)
    }

    override fun getItemCount() = things.size

    override fun onBindViewHolder(holder: ThingViewHolder, position: Int) {
        val thing = things[position]
        holder.nameTV.text = thing.name
        holder.descriptionTV.text = thing.description
        holder.imageViewIV.setImageResource(thing.image)
    }
}