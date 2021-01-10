package com.example.cafebazaartest.framework.datasource.network.abstraction

import com.example.cafebazaartest.business.domain.model.Venue

interface NetworkService {
    suspend fun getVenueList(
        lat: Double,
        lan: Double,
        query: String,
        limit : Int,
        offset:Int
    ): ArrayList<Venue>

    suspend fun getVenueDetail(id:String): Venue
}