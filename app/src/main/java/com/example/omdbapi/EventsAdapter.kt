package com.example.omdbapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbapi.R

class EventsAdapter(
    private var eventsList: List<EventsItem>,
    private val listener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var title: TextView = view.findViewById(R.id.titleText)
        var year: TextView = view.findViewById(R.id.yearText)
        var imdbID: TextView = view.findViewById(R.id.imdbIDText)
        var type: TextView = view.findViewById(R.id.typeText)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val eventItem = eventsList[position]

        holder.title.text = eventItem.title
        holder.year.text = eventItem.year
        holder.imdbID.text = eventItem.imdbID
        holder.type.text = eventItem.type

    }

    override fun getItemCount(): Int {
        return eventsList.size
    }
}
