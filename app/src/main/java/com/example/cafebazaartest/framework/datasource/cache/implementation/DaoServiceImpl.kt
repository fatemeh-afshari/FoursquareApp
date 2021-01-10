package com.example.cafebazaartest.framework.datasource.cache.implementation

import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.framework.datasource.cache.abstraction.DaoService
import com.example.cafebazaartest.framework.datasource.cache.database.Dao
import com.example.cafebazaartest.framework.datasource.cache.mappers.CacheMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaoServiceImpl
@Inject
constructor(
    private val dao: Dao,
    private val venueMapper: CacheMapper

    ): DaoService{
    override suspend fun getVenueList(): List<Venue> {
        return dao.getVenueList().let { list->venueMapper.entityListToVenueList(list) }
    }

    override suspend fun getVenueDetail(id: String): Venue {
        return dao.getVenueDetail(id).let { entity->venueMapper.mapFromEntity(entity) }
    }

    override suspend fun addVenueList(venues: List<Venue>): LongArray {
        return  dao.addVenueList(venueMapper.venueListToEntityList(venues))
    }

    override suspend fun addVenueDetial(venue: Venue): Long {
       return dao.addVenueDetial(venueMapper.mapToEntity(venue))
    }

    override suspend fun deleteAllVenues():Int {
       return dao.deleteAllVenues()
    }
}