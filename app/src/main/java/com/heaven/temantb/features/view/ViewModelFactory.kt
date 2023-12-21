package com.heaven.temantb.features.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.di.Injection
import com.heaven.temantb.features.view.healthMonitorAdd.HealthMonitorViewModel
import com.heaven.temantb.features.view.healthMonitorList.HealthListViewModel
import com.heaven.temantb.features.view.login.LoginViewModel
import com.heaven.temantb.features.view.main.MainViewModel
import com.heaven.temantb.features.view.medicineScheduleAdd.MedicineScheduleViewModel
import com.heaven.temantb.features.view.healthMonitorDetail.DetailHealthViewModel
import com.heaven.temantb.features.view.medicineScheduleDetail.DetailScheduleViewModel
import com.heaven.temantb.features.view.medicineScheduleList.ScheduleListViewModel
import com.heaven.temantb.features.view.signup.SignUpViewModel

class ViewModelFactory(private val repository: GeneralRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MedicineScheduleViewModel::class.java) -> {
                MedicineScheduleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScheduleListViewModel::class.java) -> {
                ScheduleListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailScheduleViewModel::class.java) -> {
                DetailScheduleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HealthMonitorViewModel::class.java) -> {
                HealthMonitorViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HealthListViewModel::class.java) -> {
                HealthListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailHealthViewModel::class.java) -> {
                DetailHealthViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}