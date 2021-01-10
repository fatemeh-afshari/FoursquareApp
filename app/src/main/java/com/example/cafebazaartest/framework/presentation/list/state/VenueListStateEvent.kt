package com.example.cafebazaartest.framework.presentation.list.state

import com.example.cafebazaartest.business.domain.state.StateEvent

sealed class VenueListStateEvent : StateEvent {
    class GetVenueListEvent(
        val clearLayoutManagerState: Boolean = true,
        val lat: Double,
        val lon: Double,
        val limit: Int,
        val query: String,
        val offset: Int
    ) : VenueListStateEvent() {

        override fun errorInfo(): String {
            return "Error getting list of venues."
        }

        override fun eventName(): String {
            return "GetVenueListEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
    class GetVenueListEventFromCache(
        val clearLayoutManagerState: Boolean = true
    ) : VenueListStateEvent() {

        override fun errorInfo(): String {
            return "Error getting list of venues."
        }

        override fun eventName(): String {
            return "GetVenueListEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}