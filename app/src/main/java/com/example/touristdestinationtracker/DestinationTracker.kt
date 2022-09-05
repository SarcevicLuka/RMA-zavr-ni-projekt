package com.example.touristdestinationtracker

import android.app.Application
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.example.touristdestinationtracker.di.databaseModule
import com.example.touristdestinationtracker.di.repositoryModule
import com.example.touristdestinationtracker.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DestinationTracker: Application() {
     override fun onCreate() {
         Log.d("STARTED","Pokrenuta aplikacija")
         super.onCreate()
         application = this

         startKoin {
             androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
             androidContext(this@DestinationTracker)
             modules(
                 databaseModule,
                 repositoryModule,
                 viewmodelModule
             )
         }

     }

    companion object{
        lateinit var application: Application
    }
}