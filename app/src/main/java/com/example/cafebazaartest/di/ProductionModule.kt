package com.example.cafebazaartest.di

import androidx.room.Room
import com.example.cafebazaartest.framework.datasource.cache.database.VenueDatabase
import com.example.cafebazaartest.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@FlowPreview
@Module
object ProductionModule {
@JvmStatic
@Singleton

@Provides
fun provideVenueDb(app: BaseApplication): VenueDatabase {
    return Room
        .databaseBuilder(app, VenueDatabase::class.java, VenueDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}}