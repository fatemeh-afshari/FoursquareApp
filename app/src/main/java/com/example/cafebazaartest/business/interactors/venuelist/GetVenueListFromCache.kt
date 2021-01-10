package com.example.cafebazaartest.business.interactors.venuelist

import com.example.cafebazaartest.business.data.cache.abstraction.CacheDataSource
import com.example.cafebazaartest.business.data.network.ApiResponseHandler
import com.example.cafebazaartest.business.data.util.safeApiCall
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.*
import com.example.cafebazaartest.framework.presentation.list.state.VenueListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVenueListFromCache (
    private val cacheDataSource: CacheDataSource
) {
    fun getVenuseList(stateEvent: StateEvent): Flow<DataState<VenueListViewState>?> =
        flow {

            val apiResult = safeApiCall(Dispatchers.IO) {
                cacheDataSource.getVenueList(

                )
            }

            val response = object : ApiResponseHandler<VenueListViewState, ArrayList<Venue>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: ArrayList<Venue>): DataState<VenueListViewState>? {
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
        }

    companion object{
        const val SEARCH_NOTES_SUCCESS = "Successfully done."

    }
}