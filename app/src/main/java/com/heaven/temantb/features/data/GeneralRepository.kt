package com.heaven.temantb.features.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.heaven.temantb.features.data.dataClass.HealthRequest
import com.heaven.temantb.features.data.dataClass.LoginRequest
import com.heaven.temantb.features.data.dataClass.MedicineScheduleRequest
import com.heaven.temantb.features.data.dataClass.UserRequest
import com.heaven.temantb.features.data.di.AlertIndicator
import com.heaven.temantb.features.data.pref.UserModel
import com.heaven.temantb.features.data.pref.UserPreference
import com.heaven.temantb.features.data.pref.retrofit.ApiService
import com.heaven.temantb.features.data.pref.retrofit.response.DetailHealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.DetailScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.HealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListHealthResponse
import com.heaven.temantb.features.data.pref.retrofit.response.ListScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.LoginResponse
import com.heaven.temantb.features.data.pref.retrofit.response.MedicineScheduleResponse
import com.heaven.temantb.features.data.pref.retrofit.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

class GeneralRepository private constructor(
    private val context: Context,
    private val scheduleApiService: ApiService,
    private val healthApiService: ApiService,
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
            val response = scheduleApiService.login(loginRequest)
            if (response.error) {
                emit(AlertIndicator.Error(response.message))
            } else {
                if (response.loginResult.token.isNullOrEmpty()) {
                    // Handle null or empty token
                    emit(AlertIndicator.Error("Token is null or empty"))
                } else {
                    emit(AlertIndicator.Success(response))
                    saveSession(UserModel(email, response.loginResult.token, true, response.loginResult.userId))
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
            val response = scheduleApiService.users(userRequest)
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
        userId: String
    ): LiveData<AlertIndicator<MedicineScheduleResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            if (token.isEmpty()) {
                emit(AlertIndicator.Error("User not logged in"))
                return@liveData
            }

            val response = scheduleApiService.uploadMedicineSchedule(
                "Bearer $token",
                MedicineScheduleRequest(medicineName, description, hour, userId)
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

    fun getSchedule(token: String, userId: String): LiveData<AlertIndicator<ListScheduleResponse>> = liveData{
        emit(AlertIndicator.Loading)
        try {
            val response = scheduleApiService.getSchedule("Bearer $token", userId)
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

    fun getDetailSchedule(scheduleId: String, token: String): LiveData<AlertIndicator<DetailScheduleResponse>> = liveData{
        emit(AlertIndicator.Loading)
        try {
            val response = scheduleApiService.getDetailSchedule(scheduleId,"Bearer $token")
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

    suspend fun deleteSchedule(token: String, scheduleId: String) {
        try {
            val response = scheduleApiService.deleteSchedule("Bearer $token", scheduleId)

            val requestHeaders = response.raw().request.headers
        } catch (e: Exception) {
            Log.e("DeleteSchedule", "Error: ${e.message}", e)
        }
    }

    fun uploadHealth(
        token: String,
        description: String,
        userId: String
    ): LiveData<AlertIndicator<HealthResponse>> = liveData {
        emit(AlertIndicator.Loading)
        try {
            if (token.isEmpty()) {
                emit(AlertIndicator.Error("User not logged in"))
                return@liveData
            }
            val response = healthApiService.uploadHealth(
                "Bearer $token",
                HealthRequest(description, userId)
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

    fun getHealth(token: String, userId: String): LiveData<AlertIndicator<ListHealthResponse>> = liveData{
        emit(AlertIndicator.Loading)
        try {
            val response = scheduleApiService.getHealth("Bearer $token", userId)
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

    suspend fun deleteHealth(token: String, healthId: String) {
        try {
            val response = scheduleApiService.deleteHealth("Bearer $token", healthId)

            val requestHeaders = response.raw().request.headers
        } catch (e: Exception) {
            Log.e("DeleteSchedule", "Error: ${e.message}", e)
        }
    }

    fun getDetailHealth(healthId: String, token: String): LiveData<AlertIndicator<DetailHealthResponse>> = liveData{
        emit(AlertIndicator.Loading)
        try {
            val response = scheduleApiService.getDetailHealth(healthId,"Bearer $token")
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

    companion object {
        @Volatile
        private var instance: GeneralRepository? = null

        fun getInstance(
            context: Context,
            scheduleApiService: ApiService,
            healthApiService: ApiService,
            userPreference: UserPreference
        ): GeneralRepository =
            instance ?: synchronized(this) {
                instance ?: GeneralRepository(context, scheduleApiService, healthApiService, userPreference)
            }.also { instance = it }
    }

}