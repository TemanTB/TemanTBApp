package com.heaven.temantb.features.view.medicineScheduleAdd

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class MedicineScheduleViewModel(private val repository: GeneralRepository) : ViewModel() {
    fun uploadMedicineSchedule(token: String, medicineName: String, description: String, hour: String, userID: String) = repository.uploadMedicineSchedule(token, medicineName, description, hour, userID)
}

