package com.example.touristdestinationtracker.data

import androidx.lifecycle.LiveData
import com.example.touristdestinationtracker.model.Destination

interface DestinationRepository {

    fun save(destination: Destination)
    fun delete(destination: Destination)
    fun getDestinationById(id: Long): Destination?
    fun getAllDestinations(): LiveData<List<Destination>>
}