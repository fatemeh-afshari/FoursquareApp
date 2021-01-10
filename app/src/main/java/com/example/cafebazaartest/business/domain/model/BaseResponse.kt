package com.example.cafebazaartest.business.domain.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: T
)