package com.heaven.temantb.login.view.login

import androidx.lifecycle.ViewModel
import com.heaven.temantb.login.data.GeneralRepository

class LoginViewModel(private val repository: GeneralRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}