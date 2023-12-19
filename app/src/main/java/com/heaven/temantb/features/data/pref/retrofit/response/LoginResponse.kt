package com.heaven.temantb.features.data.pref.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userID")
    val userID: String,

    @field:SerializedName("token")
    val token: String
)
