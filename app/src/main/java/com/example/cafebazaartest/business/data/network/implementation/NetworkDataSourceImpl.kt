package com.example.cafebazaartest.business.data.network.implementation

import com.example.cafebazaartest.business.data.network.abstraction.NetworkDataSource
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.framework.datasource.network.abstraction.NetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSourceImpl
@Inject
constructor(
    private val networkService: NetworkService
):NetworkDataSource {
    override suspend fun getVenueList(
        lat: Double,
        lan: Double,
        query: String,
        limit: Int,
        offset:Int
    ): ArrayList<Venue> {
       return networkService.getVenueList(lat , lan , query , limit , offset);
    }

    override suspend fun getVenueDetail(id: String): Venue {
        return networkService.getVenueDetail(id);
    }
}