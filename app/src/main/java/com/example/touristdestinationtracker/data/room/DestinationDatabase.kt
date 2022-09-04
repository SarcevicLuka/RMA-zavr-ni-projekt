package com.example.touristdestinationtracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.touristdestinationtracker.data.DestinationDao
import com.example.touristdestinationtracker.model.Destination


@Database(
    entities = [Destination::class],
    version = 9,
    exportSchema = false
)

@TypeConverters(DestinationConverters::class)
abstract class DestinationDatabase : RoomDatabase() {

    abstract fun getDestinationDao(): DestinationDao

    companion object {

        private const val databaseName = "destinationsDB"

        @Volatile
        private var INSTANCE: DestinationDatabase? = null

        fun getDatabase(context: Context): DestinationDatabase {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): DestinationDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DestinationDatabase::class.java,
                databaseName
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}