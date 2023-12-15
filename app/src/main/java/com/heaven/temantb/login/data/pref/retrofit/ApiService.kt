package com.heaven.temantb.login.data.pref.retrofit

import com.heaven.temantb.login.data.dataClass.LoginRequest
import com.heaven.temantb.login.data.dataClass.MedicineScheduleRequest
import com.heaven.temantb.login.data.dataClass.UserRequest
import com.heaven.temantb.login.data.pref.retrofit.response.LoginResponse
import com.heaven.temantb.login.data.pref.retrofit.response.MedicineScheduleResponse
import com.heaven.temantb.login.data.pref.retrofit.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("users")
    suspend fun users(@Body userRequest: UserRequest): SignUpResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

//    @POST("schedule")
//    suspend fun uploadMedicineSchedule(
//        @Header("Authorization") token: String,
//        @Query("medicineName") medicineName: RequestBody,
//        @Query("description") description: RequestBody,
//        @Query("hour") hour: RequestBody
//    ): MedicineScheduleResponse

    @POST("schedule")
    suspend fun uploadMedicineSchedule(
        @Header("Authorization") token: String,
        @Body request: MedicineScheduleRequest
    ): MedicineScheduleResponse


//    @GET("stories")
//    suspend fun getStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int = 1,
//        @Query("size") size: Int = 20,
//    ): StoryResponse
//
//    @GET("stories/{id}")
//    suspend fun getDetailStory(
//        @Path("id") id: String,
//        @Header("Authorization") token: String
//    ): DetailStoryResponse
//
//    @Multipart
//    @POST("stories")
//    suspend fun uploadImage(
//    @Header("Authorization") token: String,
//    @Part file: MultipartBody.Part,
//    @Part("description") description: RequestBody,
//    ): MedicineScheduleResponse
//
//    @GET("stories")
//    suspend fun getStoriesWithLocation(
//        @Header("Authorization") token: String,
//        @Query("location") location : Int = 1,
//    ): StoryResponse
}


