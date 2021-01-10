package com.example.cafebazaartest.framework.datasource.cache.abstraction

import com.example.cafebazaartest.business.domain.model.Venue

interface DaoService {
    suspend fun getVenueList( ): List<Venue>

    suspend fun getVenueDetail(id:String): Venue
    suspend fun addVenueList(venues: List<Venue>): LongArray
    suspend fun addVenueDetial(venue: Venue): Long
    suspend fun deleteAllVenues():Int

}