package com.example.cafebazaartest.framework.presentation.detial.state

import com.example.cafebazaartest.business.domain.state.StateEvent
import com.example.cafebazaartest.framework.presentation.list.state.VenueListStateEvent


sealed class VenueDetailStateEvent: StateEvent {
    class GetVenueDetailEvent(
        val clearLayoutManagerState: Boolean = true,
        val id: String
    ) : VenueDetailStateEvent() {

        override fun errorInfo(): String {
            return "Error getting detail of venue."
        }

        override fun eventName(): String {
            return "GetVenueDetailEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}