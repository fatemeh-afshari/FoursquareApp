package com.example.cafebazaartest.business.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("contact")
    val contact:Contact?,
    @SerializedName("location")
    val location:Location

):Parcelable

@Parcelize
data class Contact(
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("formattedPhone")
    val formattedPhone: String?
    ): Parcelable
@Parcelize
data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("crossStreet")
    val crossStreet: String?,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
):Parcelable