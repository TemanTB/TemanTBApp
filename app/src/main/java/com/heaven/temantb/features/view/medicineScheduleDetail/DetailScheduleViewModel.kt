package com.heaven.temantb.features.view.medicineScheduleDetail

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class DetailScheduleViewModel (private val repository: GeneralRepository) : ViewModel() {

    fun getDetailSchedule(scheduleID: String, token: String) = repository.getDetailSchedule(scheduleID, token)
}

