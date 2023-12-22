package com.heaven.temantb.features.view.healthMonitorList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.pref.UserModel
import kotlinx.coroutines.launch

class HealthListViewModel(private val repository: GeneralRepository): ViewModel() {
    fun getHealth(token: String, userId: String) = repository.getHealth(token, userId)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun deleteHealth(token: String, healthId: String) {
        viewModelScope.launch {
            try {
                repository.deleteHealth(token, healthId)
            } catch (e: Exception) {
            }
        }
    }
}

