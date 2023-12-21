package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class DetailHealthResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("data")
	val data: HealthInDetail,

	@field:SerializedName("message")
	val message: String
)


data class HealthInDetail(

	@field:SerializedName("healthId")
	val healthId: String,

	@field:SerializedName("weeks")
	val weeks: Int,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("nextDate")
	val nextDate: String,

	@field:SerializedName("point")
	val point: Int,

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
