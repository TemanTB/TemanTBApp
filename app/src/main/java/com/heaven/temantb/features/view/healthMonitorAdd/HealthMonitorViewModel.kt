package com.heaven.temantb.features.view.healthMonitorAdd

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class HealthMonitorViewModel(private val repository: GeneralRepository) : ViewModel() {
    fun uploadHealth(token:String, description: String, userId: String) = repository.uploadHealth(token, description, userId)
}