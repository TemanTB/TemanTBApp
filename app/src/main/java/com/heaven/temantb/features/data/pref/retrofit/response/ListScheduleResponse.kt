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

    @field:SerializedName("scheduleId")
    val scheduleId: String,

    @field:SerializedName("medicineName")
    val medicineName: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("hour")
    val hour: String,

    @field:SerializedName("userID")
    val userID: String,

    @field:SerializedName("user")
    val user: User
)

data class User(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String
)
