package com.example.cafebazaartest.business.data.cache.implementation

import com.example.cafebazaartest.business.data.cache.abstraction.CacheDataSource
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.framework.datasource.cache.abstraction.DaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheDataSourceImpl
@Inject
constructor(
    private val daoService: DaoService
): CacheDataSource {
    override suspend fun getVenueList(): ArrayList<Venue> {
        return daoService.getVenueList() as ArrayList<Venue>
    }

    override suspend fun getVenueDetail(id: String): Venue {
        return daoService.getVenueDetail(id)
    }

    override suspend fun addVenueList(venues: List<Venue>): LongArray {
        return daoService.addVenueList(venues)
    }


    override suspend fun addVenueDetial(venue: Venue): Long {
        return daoService.addVenueDetial(venue)
    }

    override suspend fun deleteAllVenues():Int {
        return daoService.deleteAllVenues()
    }


}