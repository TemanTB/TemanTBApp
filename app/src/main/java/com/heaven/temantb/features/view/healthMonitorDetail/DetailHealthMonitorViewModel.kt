package com.heaven.temantb.features.view.healthMonitorDetail

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class DetailHealthViewModel (private val repository: GeneralRepository) : ViewModel() {
    fun getDetailHealth(healthId: String, token: String) = repository.getDetailHealth(healthId, token)
}

