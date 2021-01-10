package com.example.cafebazaartest.framework.datasource.network.implementation

import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.framework.datasource.network.abstraction.NetworkService
import com.example.cafebazaartest.framework.datasource.network.api.Api
import com.example.cafebazaartest.util.CLIENT_ID
import com.example.cafebazaartest.util.CLIENT_SECRET
import com.example.cafebazaartest.util.VERSION
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkServiceImpl
@Inject
constructor(
    private val api: Api
) : NetworkService {
    override suspend fun getVenueList(
        lat: Double,
        lan: Double,
        query: String,
        limit: Int,
        offset:Int
    ): ArrayList<Venue> {
        return api.getVenueList(CLIENT_ID, CLIENT_SECRET, VERSION, "$lat,$lan", query, limit,offset)
            .blockingGet().response.groups[0].items.map { trasform->trasform.venue } as ArrayList<Venue>
    }

    override suspend fun getVenueDetail(id: String): Venue {
        return api.getVenueDetail(id , CLIENT_ID , CLIENT_SECRET , VERSION).blockingGet().response.venue
    }
}