package com.example.cafebazaartest.framework.datasource.network.api

import com.example.cafebazaartest.business.domain.model.BaseResponse
import com.example.cafebazaartest.business.domain.model.DetailResponse
import com.example.cafebazaartest.business.domain.model.ListResponse
import com.example.cafebazaartest.business.domain.model.Venue
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single
import retrofit2.http.Path

public interface Api {


    @GET("explore")
     fun  getVenueList(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("v") vrsion:String,
        @Query("ll") latlng:String,
        @Query("query") query:String,
        @Query("limit") limit:Int,
        @Query("offset")offset:Int
    ): Single<BaseResponse<ListResponse>>

    @GET("{id}")
     fun getVenueDetail(
        @Path("id") id: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("v") vrsion:String
    ): Single<BaseResponse<DetailResponse>>

}