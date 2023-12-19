package com.heaven.temantb.features.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val userId: String
)