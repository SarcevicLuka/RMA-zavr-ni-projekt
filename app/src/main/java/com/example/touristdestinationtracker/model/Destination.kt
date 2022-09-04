package com.example.touristdestinationtracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "destinations")
data class Destination (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "location_country")
    val locationCountry: String,
    @ColumnInfo(name = "location_city")
    val locationCity: String,
    @ColumnInfo(name = "short_description")
    val shortDescription: String,
    @ColumnInfo(name = "dateAdded")
    val dateAdded: String,
    @ColumnInfo(name = "image")
    val image: ByteArray,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
) {
}