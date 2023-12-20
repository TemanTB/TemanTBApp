package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class HealthResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String

)

