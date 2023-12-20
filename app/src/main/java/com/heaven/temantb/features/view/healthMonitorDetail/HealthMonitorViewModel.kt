package com.heaven.temantb.features.view.medicineScheduleDetail

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class DetailHealthViewModel (private val repository: GeneralRepository) : ViewModel() {

    fun getDetailHealth(scheduleId: String, token: String) = repository.getDetailHealth(scheduleId, token)
}

