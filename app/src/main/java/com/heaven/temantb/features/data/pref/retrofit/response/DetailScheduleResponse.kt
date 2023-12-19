package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class DetailScheduleResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("data")
	val data: List<ScheduleInDetail>,

	@field:SerializedName("message")
	val message: String
)

data class ScheduleInDetail(

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

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
