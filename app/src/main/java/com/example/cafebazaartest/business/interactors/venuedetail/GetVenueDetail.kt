package com.example.cafebazaartest.business.interactors.venuedetail

import com.example.cafebazaartest.business.data.network.ApiResponseHandler
import com.example.cafebazaartest.business.data.network.abstraction.NetworkDataSource
import com.example.cafebazaartest.business.data.util.safeApiCall
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.*
import com.example.cafebazaartest.framework.presentation.detial.state.VenueDetailViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class GetVenueDetail(
    private val networkDataSource: NetworkDataSource
) {
    fun getDetial(id: String, stateEvent: StateEvent): Flow<DataState<VenueDetailViewState>?> =
        flow {

            val apiResult = safeApiCall(Dispatchers.IO) {
                networkDataSource.getVenueDetail(
                    id = id
                )
            }

            val response = object : ApiResponseHandler<VenueDetailViewState, Venue>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: Venue): DataState<VenueDetailViewState>? {
                    val message: String? =
                        SEARCH_NOTES_SUCCESS
                    val uiComponentType: UIComponentType? = UIComponentType.None()
                    return DataState.data(
                        response = Response(
                            message = message,
                            uiComponentType = uiComponentType as UIComponentType,
                            messageType = MessageType.Success()
                        ),
                        data = VenueDetailViewState(
                            venue = resultObj
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