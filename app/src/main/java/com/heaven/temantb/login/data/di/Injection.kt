package com.heaven.temantb.login.data.di

import android.content.Context
import com.heaven.storyapp.view.data.retrofit.ApiConfig
import com.heaven.temantb.login.data.GeneralRepository
import com.heaven.temantb.login.data.pref.UserPreference
import com.heaven.temantb.login.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): GeneralRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return GeneralRepository.getInstance(apiService,pref)
    }
}