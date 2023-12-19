package com.heaven.temantb.features.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.heaven.temantb.features.data.dataClass.LoginRequest
import com.heaven.temantb.features.data.dataClass.MedicineScheduleRequest
import com.heaven.temantb.features.data.dataClass.UserRequest
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.data.pref.UserModel
import com.heaven.temantb.features.data.pref.UserPreference
import com.heaven.temantb.features.data.pref.retrofit.ApiService
import com.heaven.temantb.features.data.pref.retrofit.response.DetailScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.LoginResponse
import com.heaven.temantb.features.data.pref.retrofit.response.MedicineScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

class GeneralRepository private constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
        private suspend fun saveSession(user: UserModel) {
            userPreference.saveSession(user)
        }

        fun getSession(): Flow<UserModel> {
            return userPreference.getSession()
        }

        suspend fun logout() {
            userPreference.logout()
        }

        private fun handleError(exception: Exception): AlertIndicator.Error {
            return AlertIndicator.Error(exception.message.toString())
        }

    fun login(email: String, password: String): LiveData<AlertIndicator<LoginResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)
            Log.d("GeneralRepository", "LoginResult: $response.loginResult")
            if (response.error) {
                emit(AlertIndicator.Error(response.message))
            } else {
                if (response.loginResult.token.isNullOrEmpty() || response.loginResult.userID.isNullOrEmpty()) {
                    emit(AlertIndicator.Error("Token or UserID is null or empty"))
                } else {
                    val userModel = UserModel(email, response.loginResult.token, true, response.loginResult.userID)
                    emit(AlertIndicator.Success(response))
                    saveSession(userModel)
                }
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    fun signUp(name: String, email: String, phone: String, password: String, confPassword: String): LiveData<AlertIndicator<SignUpResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            val userRequest = UserRequest(name, email, phone, password, confPassword)
            val response = apiService.users(userRequest)
            if (response.error) {
                emit(AlertIndicator.Error(response.message))
            } else {
                emit(AlertIndicator.Success(response))
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    fun uploadMedicineSchedule(
        token: String,
        medicineName: String,
        description: String,
        hour: String,
        userID: String
    ): LiveData<AlertIndicator<MedicineScheduleResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            if (token.isEmpty()) {
                emit(AlertIndicator.Error("User not logged in"))
                return@liveData
            }

            val response = apiService.uploadMedicineSchedule(
                "Bearer $token",
                MedicineScheduleRequest(medicineName, description, hour, userID)
            )

            if (response.error) {
                emit(AlertIndicator.Error(response.message))
            } else {
                emit(AlertIndicator.Success(response))
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    fun getSchedule(token: String, userID: String): LiveData<AlertIndicator<ListScheduleResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            val response = apiService.getSchedule("Bearer $token", userID)

            if (response.error) {
                emit(AlertIndicator.Error(response.message))
            } else {
                emit(AlertIndicator.Success(response))
            }
        } catch (e: Exception) {
            emit(AlertIndicator.Error(e.message.toString()))
        }
    }



    fun getDetailSchedule(scheduleID: String, token: String): LiveData<AlertIndicator<DetailScheduleResponse>> = liveData{
        Log.d("DetailScheduleModelapi", "API Request - ID: $scheduleID, Token: $token")
        Log.d("DetailScheduleViewModel", "ID: $scheduleID, Token: $token")
        emit(AlertIndicator.Loading)
        try {
            val response = apiService.getDetailSchedule(scheduleID,"Bearer $token")
            if (response.error){
                emit(AlertIndicator.Error(response.message))
            }
            else {
                emit(AlertIndicator.Success(response))
            }
        } catch (e:Exception){
            emit(AlertIndicator.Error(e.message.toString()))
        }
    }

//    fun isNotificationEnabled(): Boolean {
//        val sharedPreferences = context.getSharedPreferences(
//            AppPreferences.PREF_NAME,
//            Context.MODE_PRIVATE
//        )
//        return sharedPreferences.getBoolean(
//            AppPreferences.PREF_KEY_NOTIFICATION_ENABLED,
//            AppPreferences.DEFAULT_NOTIFICATION_ENABLED
//        )
//    }

    companion object {
        @Volatile
        private var instance: GeneralRepository? = null
        fun getInstance(
            context: Context,
            apiService: ApiService,
            userPreference: UserPreference
        ): GeneralRepository =
            instance ?: synchronized(this) {
                instance ?: GeneralRepository(context, apiService, userPreference)
            }.also { instance = it }
    }
}