package com.heaven.temantb.features.data.pref.retrofit

import com.heaven.temantb.features.data.dataClass.HealthRequest
import com.heaven.temantb.features.data.dataClass.LoginRequest
import com.heaven.temantb.features.data.dataClass.MedicineScheduleRequest
import com.heaven.temantb.features.data.dataClass.UserRequest
import com.heaven.temantb.features.data.pref.retrofit.response.DetailHealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.DetailScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.HealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListHealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.LoginResponse
import com.heaven.temantb.features.data.pref.retrofit.response.MedicineScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("schedule/{userId}")
    suspend fun getSchedule(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): ListScheduleResponse

    @GET("getschedule/{scheduleId}")
    suspend fun getDetailSchedule(
        @Path("scheduleId") scheduleId: String,
        @Header("Authorization") token: String,
    ): DetailScheduleResponse

    @DELETE("schedule/{scheduleId}")
    suspend fun deleteSchedule(
        @Header("Authorization") token: String,
        @Path("scheduleId") scheduleId: String
    ): Response<Unit>

    @POST("health")
    suspend fun uploadHealth(
        @Header("Authorization") token: String,
        @Body request: HealthRequest
    ): HealthResponse

    @GET("health/{userId}")
    suspend fun getHealth(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): ListHealthResponse

    @GET("healthId/{healthId}")
    suspend fun getDetailHealth(
        @Path("healthId") healthId: String,
        @Header("Authorization") token: String,
    ): DetailHealthResponse

    @DELETE("health/{healthId}")
    suspend fun deleteHealth(
        @Header("Authorization") token: String,
        @Path("healthId") healthId: String
    ): Response<Unit>
}


