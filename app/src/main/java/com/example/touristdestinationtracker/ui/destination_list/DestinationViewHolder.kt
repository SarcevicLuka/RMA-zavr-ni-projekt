package com.example.touristdestinationtracker.ui.destination_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.touristdestinationtracker.data.room.DestinationConverters
import com.example.touristdestinationtracker.databinding.DestinationItemBinding
import com.example.touristdestinationtracker.model.Destination

class DestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(destination: Destination) {
        val converter = DestinationConverters()
        val binding = DestinationItemBinding.bind(itemView)
        binding.destinationItemLocation.text = destination.locationCity + ", " + destination.locationCountry
        binding.destinationItemDateAdded.text = destination.dateAdded
        binding.destinationThumbnail.setImageBitmap(converter.convertByteArrayToImage(destination.image))
    }
}