package com.heaven.temantb.login.view.medicineSchedule

import androidx.lifecycle.ViewModel
import com.heaven.temantb.login.data.GeneralRepository

class MedicineScheduleViewModel(private val repository: GeneralRepository) : ViewModel() {
    fun uploadMedicineSchedule(token: String, medicineName: String, description: String, hour: String) = repository.uploadMedicineSchedule(token, medicineName, description, hour)
}

