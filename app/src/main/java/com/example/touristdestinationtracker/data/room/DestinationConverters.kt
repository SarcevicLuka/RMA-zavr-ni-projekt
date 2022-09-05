package com.example.touristdestinationtracker.data.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class DestinationConverters {
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