package com.example.touristdestinationtracker.data.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class DestinationConverters {

    /*@TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }*/

    @TypeConverter
    fun convertImageToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    fun convertByteArrayToImage(array: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }
}