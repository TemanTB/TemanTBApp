package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class ListHealthResponse(

    @field:SerializedName("data")
    val listHealth: List<ListHealthItem> = emptyList(),

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ListHealthItem(

    @field:SerializedName("healthId")
    val healthId: String,

    @field:SerializedName("week")
    val week: String,

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("nextDate")
    val nextDate: String,

    @field:SerializedName("point")
    val point: String,

    @field:SerializedName("alert")
    val alert: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("average")
    val average: String,

    @field:SerializedName("images")
    val images: String,

    @field:SerializedName("time")
    val time: String,

    @field:SerializedName("userId")
    val userId: String,
)
