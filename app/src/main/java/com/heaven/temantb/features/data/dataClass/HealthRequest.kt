package com.heaven.temantb.features.data.dataClass

import com.google.gson.annotations.SerializedName

data class HealthRequest(
    @SerializedName("description") val description: String,
    @SerializedName("userId") val userId: String
)
