package com.heaven.temantb.login.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heaven.temantb.login.data.GeneralRepository
import com.heaven.temantb.login.data.di.Injection
import com.heaven.temantb.login.view.login.LoginViewModel
import com.heaven.temantb.login.view.main.MainViewModel
import com.heaven.temantb.login.view.medicineSchedule.MedicineScheduleViewModel
import com.heaven.temantb.login.view.signup.SignUpViewModel

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
//            modelClass.isAssignableFrom(DetailStoryViewModel::class.java) -> {
//                DetailStoryViewModel(repository) as T
//            }

//            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
//                UploadViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(MapViewModel::class.java) -> {
//                MapViewModel(repository) as T
//            }

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