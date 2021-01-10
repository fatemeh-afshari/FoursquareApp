package com.example.cafebazaartest.business.domain.model

import com.google.gson.annotations.SerializedName

data class ListResponse (
    @SerializedName("groups")
    val groups:ArrayList<Group>

)

data class Group (
    @SerializedName("type")
    val type:String ,
    @SerializedName("name")
    val name:String ,
    @SerializedName("items")
    val items:ArrayList<DetailResponse>
)