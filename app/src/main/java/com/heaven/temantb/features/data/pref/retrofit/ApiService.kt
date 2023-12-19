package com.heaven.temantb.features.data.pref.retrofit

import com.heaven.temantb.features.data.dataClass.LoginRequest
import com.heaven.temantb.features.data.dataClass.MedicineScheduleRequest
import com.heaven.temantb.features.data.dataClass.UserRequest
import com.heaven.temantb.features.data.pref.retrofit.response.DetailScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.LoginResponse
import com.heaven.temantb.features.data.pref.retrofit.response.MedicineScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    suspend fun users(@Body userRequest: UserRequest): SignUpResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("schedule")
    suspend fun uploadMedicineSchedule(
        @Header("Authorization") token: String,
        @Body request: MedicineScheduleRequest
    ): MedicineScheduleResponse

    @GET("schedule/{scheduleID}")
    suspend fun getDetailSchedule(
        @Header("Authorization") token: String,
        @Path("scheduleID") scheduleID: String
    ): DetailScheduleResponse

    @GET("schedule/users/{userID}")
    suspend fun getSchedule(
        @Header("Authorization") token: String,
        @Path("userID") userID: String
    ): ListScheduleResponse

}


