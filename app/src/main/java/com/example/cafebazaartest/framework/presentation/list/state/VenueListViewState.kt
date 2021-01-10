package com.example.cafebazaartest.framework.presentation.list.state

import android.os.Parcelable
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueListViewState(

    var venues: ArrayList<Venue>? = null




) : Parcelable, ViewState