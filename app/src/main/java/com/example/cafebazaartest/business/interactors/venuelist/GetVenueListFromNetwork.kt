package com.example.cafebazaartest.business.interactors.venuelist

import com.example.cafebazaartest.business.data.cache.abstraction.CacheDataSource
import com.example.cafebazaartest.business.data.network.ApiResponseHandler
import com.example.cafebazaartest.business.data.network.abstraction.NetworkDataSource
import com.example.cafebazaartest.business.data.util.safeApiCall
import com.example.cafebazaartest.business.data.util.safeCacheCall
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.*
import com.example.cafebazaartest.framework.presentation.detial.state.VenueDetailViewState
import com.example.cafebazaartest.framework.presentation.list.state.VenueListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVenueListFromNetwork(
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: CacheDataSource
) {
    fun getVenuseList(
        lat: Double,
        lon: Double,
        limit: Int,
        query: String,
        offset: Int,
        stateEvent: StateEvent
    ): Flow<DataState<VenueListViewState>?> =
        flow {
            var result:ArrayList<Venue>?=null
            if (offset == 0) {
                safeCacheCall(Dispatchers.IO) { cacheDataSource.deleteAllVenues() }
            }
            val apiResult = safeApiCall(Dispatchers.IO) {
                networkDataSource.getVenueList(
                    lat = lat,
                    lan = lon,
                    limit = limit,
                    query = query,
                    offset = offset
                )
            }

            val response = object : ApiResponseHandler<VenueListViewState, ArrayList<Venue>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: ArrayList<Venue>): DataState<VenueListViewState>? {
                    result=ArrayList(resultObj)
                    val message: String? =
                        SEARCH_NOTES_SUCCESS
                    val uiComponentType: UIComponentType? = UIComponentType.None()
                    return DataState.data(
                        response = Response(
                            message = message,
                            uiComponentType = uiComponentType as UIComponentType,
                            messageType = MessageType.Success()
                        ),
                        data = VenueListViewState(
                            venues = ArrayList(resultObj)
                        ),
                        stateEvent = stateEvent
                    )
                }
            }.getResult()

            emit(response)

            if (result != null) {
                safeCacheCall(Dispatchers.IO) { cacheDataSource.addVenueList(result!!) }
            }

        }

    companion object {
        const val SEARCH_NOTES_SUCCESS = "Successfully done."

    }
}