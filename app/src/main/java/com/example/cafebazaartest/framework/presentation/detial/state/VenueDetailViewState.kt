package com.example.cafebazaartest.framework.presentation.detial.state

import android.os.Parcelable
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueDetailViewState(

    var venue: Venue? = null

) : Parcelable, ViewState