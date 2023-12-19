package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class ListScheduleResponse(

    @field:SerializedName("data")
    val listSchedule: List<ListScheduleItem> = emptyList(),

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ListScheduleItem(

    @field:SerializedName("scheduleID")
    val scheduleID: String,

    @field:SerializedName("medicineName")
    val medicineName: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("hour")
    val hour: String,

    @field:SerializedName("userID")
    val userID: String,
)
