package com.heaven.temantb.features.view.medicineScheduleList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.pref.UserModel
import kotlinx.coroutines.launch

class ScheduleListViewModel(private val repository: GeneralRepository): ViewModel() {
    fun getSchedule(token: String, userId: String) = repository.getSchedule(token, userId)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    fun deleteSchedule(token: String, scheduleId: String) {
        viewModelScope.launch {
            try {
                repository.deleteSchedule(token, scheduleId)
                Log.d("DeleteSchedule", "Successfully deleted schedule on the server.")
                Log.d("DeleteSchedule", "Token: $token, ScheduleId: $scheduleId")

            } catch (e: Exception) {
                Log.e("DeleteSchedule", "Error deleting schedule: ${e.message}", e)
                Log.d("DeleteSchedule", "Token: $token, ScheduleId: $scheduleId")
            }
        }
    }
}

