package com.example.cafebazaartest.framework.presentation.detial

import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.DataState
import com.example.cafebazaartest.business.domain.state.StateEvent
import com.example.cafebazaartest.business.interactors.venuedetail.DetailInteractors
import com.example.cafebazaartest.business.interactors.venuelist.ListInteractors
import com.example.cafebazaartest.framework.presentation.common.BaseViewModel
import com.example.cafebazaartest.framework.presentation.detial.state.VenueDetailStateEvent.*
import com.example.cafebazaartest.framework.presentation.detial.state.VenueDetailViewState
import com.example.cafebazaartest.framework.presentation.list.state.VenueListViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class VenueDetailViewModel
@Inject
constructor(
    private val interactors: DetailInteractors
) : BaseViewModel<VenueDetailViewState>() {
    override fun handleNewData(data: VenueDetailViewState) {
        data.let { viewState ->
            viewState.venue?.let { venue ->
                setVenueListData(venue)
            }

        }
    }
    private fun setVenueListData(venue: Venue) {
        val update = getCurrentViewStateOrNew()
        update.venue = venue
        setViewState(update)
    }


    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<VenueDetailViewState>?> = when (stateEvent) {

            is GetVenueDetailEvent -> {
                interactors.getVenueDetial.getDetial(
                    id = stateEvent.id,
                    stateEvent = stateEvent
                )
            }
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): VenueDetailViewState {
        return VenueDetailViewState()
    }
    fun retriveVenueDetail(id:String) {
        setStateEvent(
           GetVenueDetailEvent(
                id=id
            )
        )
    }
}