package com.example.touristdestinationtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristdestinationtracker.model.Destination

@Dao
interface DestinationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(destination: Destination)

    @Delete
    fun delete(destination: Destination)

    @Query("SELECT * FROM destinations WHERE id =:id")
    fun getDestinationById(id: Long): Destination?

    @Query("SELECT * FROM destinations")
    fun getAllDestinations(): LiveData<List<Destination>>
}