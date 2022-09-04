package com.example.touristdestinationtracker.di

import android.app.Application
import com.example.touristdestinationtracker.data.DestinationDao
import com.example.touristdestinationtracker.data.DestinationRepository
import com.example.touristdestinationtracker.data.DestinationRepositoryImpl
import com.example.touristdestinationtracker.data.room.DestinationDatabase
import com.example.touristdestinationtracker.ui.destination_details.DestinationDetailsViewModel
import com.example.touristdestinationtracker.ui.destination_list.DestinationListViewModel
import com.example.touristdestinationtracker.ui.destination_new.DestinationNewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): DestinationDatabase {
        return DestinationDatabase.getDatabase(application)
    }
    fun provideDestinationDao(database: DestinationDatabase): DestinationDao {
        return database.getDestinationDao()
    }
    single<DestinationDatabase>{ provideDatabase(get()) }
    single<DestinationDao> { provideDestinationDao(get()) }
}

val repositoryModule = module {
    single<DestinationRepository> { DestinationRepositoryImpl(get()) }
}

val viewmodelModule = module {
    viewModel { DestinationListViewModel(get()) }
    viewModel { DestinationDetailsViewModel(get()) }
    viewModel { DestinationNewViewModel(get()) }
}