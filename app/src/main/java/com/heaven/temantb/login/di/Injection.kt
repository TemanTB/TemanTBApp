package com.heaven.temantb.login.di

import android.content.Context
import com.heaven.temantb.login.data.UserRepository
import com.heaven.temantb.login.data.pref.UserPreference
import com.heaven.temantb.login.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}