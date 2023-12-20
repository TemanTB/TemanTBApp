package com.heaven.temantb.features.data.pref.retrofit

import com.heaven.temantb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    enum class ApiType {
        SCHEDULE,
        HEALTH
    }

    fun getApiService(apiType: ApiType): ApiService {
        val baseUrl = when (apiType) {
            ApiType.SCHEDULE -> BuildConfig.API_URL_SCHEDULE
            ApiType.HEALTH -> BuildConfig.API_URL_HEALTH
        }

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}


