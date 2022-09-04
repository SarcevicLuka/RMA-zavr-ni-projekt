package com.example.touristdestinationtracker.ui.destination_new

import androidx.lifecycle.ViewModel
import com.example.touristdestinationtracker.data.DestinationRepository
import com.example.touristdestinationtracker.model.Destination

class DestinationNewViewModel(
    private val destinationRepository: DestinationRepository
): ViewModel() {
    fun save(description: String, date: String, city: String, country: String, image: ByteArray, latitude: String, longitude: String) {
        destinationRepository.save(Destination(0,country, city, description, date, image, latitude, longitude))
    }
}