package com.heaven.temantb.login.data.pref.retrofit

import com.heaven.storyapp.view.data.retrofit.response.LoginResponse
import com.heaven.storyapp.view.data.retrofit.response.SignUpResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confPassword") confPassword: String,
        @Field("phone") phone: String,

    ): SignUpResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confPassword") confPassword: String
    ): LoginResponse

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
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//    ): FileUploadResponse
//
//    @GET("stories")
//    suspend fun getStoriesWithLocation(
//        @Header("Authorization") token: String,
//        @Query("location") location : Int = 1,
//    ): StoryResponse
}


