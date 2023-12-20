//package com.heaven.temantb.features.data.di
//
//import android.content.Context
//import com.heaven.temantb.features.data.GeneralRepository
//import com.heaven.temantb.features.data.pref.UserPreference
//import com.heaven.temantb.features.data.pref.dataStore
//import com.heaven.temantb.features.data.pref.retrofit.ApiConfig
//
//object Injection {
//    fun provideRepository(context: Context): GeneralRepository {
//        val apiService = ApiConfig.getApiService()
//        val pref = UserPreference.getInstance(context.dataStore)
//        return GeneralRepository.getInstance(context,apiService,pref)
//    }
//}

package com.heaven.temantb.features.data.di

import android.content.Context
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.pref.UserPreference
import com.heaven.temantb.features.data.pref.dataStore
import com.heaven.temantb.features.data.pref.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): GeneralRepository {
        val scheduleApiService = ApiConfig.getApiService(ApiConfig.ApiType.SCHEDULE)
        val healthApiService = ApiConfig.getApiService(ApiConfig.ApiType.HEALTH)
        val pref = UserPreference.getInstance(context.dataStore)
        return GeneralRepository.getInstance(context, scheduleApiService, healthApiService, pref)
    }
}
