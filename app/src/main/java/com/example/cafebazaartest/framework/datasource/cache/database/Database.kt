package com.example.cafebazaartest.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cafebazaartest.framework.datasource.cache.model.VenueCacheEntity

@Database(entities = [VenueCacheEntity::class ], version = 1)
abstract class VenueDatabase: RoomDatabase() {

    abstract fun dao(): Dao

    companion object{
        val DATABASE_NAME: String = "venue_db"
    }
}