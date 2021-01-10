package com.example.cafebazaartest.business.domain.model

import com.google.gson.annotations.SerializedName

data class DetailResponse (
    @SerializedName("venue")
    val venue:Venue
)