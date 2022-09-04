package com.example.touristdestinationtracker.ui.destination_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.touristdestinationtracker.R
import com.example.touristdestinationtracker.model.Destination

class DestinationAdapter: RecyclerView.Adapter<DestinationViewHolder>() {

    private val destinations = mutableListOf<Destination>()
    var onDestinationSelectedListener: OnDestinationEventListener? = null

    fun setDestinations(destinations: List<Destination>) {
        this.destinations.clear()
        this.destinations.addAll(destinations)
        this.notifyDataSetChanged()
    }

    fun getDestinationAt(position: Int): Destination {
        return destinations[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.destination_item, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinations[position]
        holder.bind(destination)
        onDestinationSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onDestinationSelected(destination.id) }
        }
    }

    override fun getItemCount(): Int {
        return destinations.count()
    }
}