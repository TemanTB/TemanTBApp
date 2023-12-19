package com.heaven.temantb.features.data.dataClass

import com.google.gson.annotations.SerializedName

data class MedicineScheduleRequest(
    @SerializedName("medicineName") val medicineName: String,
    @SerializedName("description") val description: String,
    @SerializedName("hour") val hour: String,
    @SerializedName("userID") val userID: String
)
