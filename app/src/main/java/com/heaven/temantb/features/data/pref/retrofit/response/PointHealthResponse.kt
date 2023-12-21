package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class PointHealthResponse(

    @field:SerializedName("data")
    val data: List<PointHealthItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class PointHealthItem(

    @field:SerializedName("date")
    val name: String,

    @field:SerializedName("point")
    val userId: Int,

)
