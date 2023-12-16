package com.heaven.temantb.features.view.login

import androidx.lifecycle.ViewModel
import com.heaven.temantb.features.data.GeneralRepository

class LoginViewModel(private val repository: GeneralRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}