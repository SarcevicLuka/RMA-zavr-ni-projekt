package com.example.touristdestinationtracker.ui.destination_details

import androidx.lifecycle.ViewModel
import com.example.touristdestinationtracker.data.DestinationRepository
import com.example.touristdestinationtracker.model.Destination

class DestinationDetailsViewModel(
    private val destinationRepository: DestinationRepository
): ViewModel() {

    fun getDestinationById(id: Long?): Destination? {
        var destination: Destination? = null
        id?.let { destination = destinationRepository.getDestinationById(id) }
        return destination
    }
}