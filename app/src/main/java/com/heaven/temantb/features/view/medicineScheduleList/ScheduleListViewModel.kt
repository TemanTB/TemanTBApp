package com.heaven.temantb.features.view.medicineScheduleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.pref.UserModel

class ScheduleListViewModel(private val repository: GeneralRepository): ViewModel() {
    fun getSchedule(token: String) = repository.getSchedule(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}