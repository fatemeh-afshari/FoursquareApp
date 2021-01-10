package com.example.cafebazaartest.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.framework.datasource.cache.model.VenueCacheEntity

@Dao
interface Dao {

    @Query("SELECT * FROM venues")
    suspend fun getVenueList(): List<VenueCacheEntity>

    @Query("SELECT * FROM venues WHERE id =:id")
    suspend fun getVenueDetail(id:String): VenueCacheEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVenueList(venues: List<VenueCacheEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVenueDetial(venue: VenueCacheEntity): Long

    @Query("DELETE FROM venues")
    suspend fun deleteAllVenues():Int
}