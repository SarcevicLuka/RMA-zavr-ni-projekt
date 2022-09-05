package com.example.touristdestinationtracker.ui.destination_list

import androidx.lifecycle.ViewModel
import com.example.touristdestinationtracker.data.DestinationRepository
import com.example.touristdestinationtracker.model.Destination

class DestinationListViewModel(
    private val destinationRepository: DestinationRepository
): ViewModel() {
    var destinations = destinationRepository.getAllDestinations()


    fun delete(destination: Destination) {
        destinationRepository.delete(destination)
    }
}