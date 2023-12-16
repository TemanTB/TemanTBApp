package com.heaven.temantb.features.data.dataClass

data class UserRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val confPassword: String
)