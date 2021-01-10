package com.example.cafebazaartest.business.data.cache.abstraction

import com.example.cafebazaartest.business.domain.model.Venue

interface CacheDataSource {
    suspend fun getVenueList( ): ArrayList<Venue>

    suspend fun getVenueDetail(id:String): Venue
    suspend fun addVenueList(venues: List<Venue>): LongArray
    suspend fun addVenueDetial(venue: Venue): Long
    suspend fun deleteAllVenues():Int
}