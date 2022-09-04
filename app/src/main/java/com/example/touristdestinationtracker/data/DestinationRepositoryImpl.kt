package com.example.touristdestinationtracker.data

import androidx.lifecycle.LiveData
import com.example.touristdestinationtracker.model.Destination

class DestinationRepositoryImpl (private val destinationDao: DestinationDao) : DestinationRepository{
    override fun save(destination: Destination) {
        destinationDao.save(destination)
    }

    override fun delete(destination: Destination) {
        destinationDao.delete(destination)
    }

    override fun getDestinationById(id: Long): Destination? {
        return destinationDao.getDestinationById(id)
    }

    override fun getAllDestinations(): LiveData<List<Destination>> {
        return destinationDao.getAllDestinations()
    }
}