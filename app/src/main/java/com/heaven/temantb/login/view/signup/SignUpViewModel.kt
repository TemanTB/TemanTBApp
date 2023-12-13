package com.heaven.temantb.login.view.signup

import androidx.lifecycle.ViewModel
import com.heaven.temantb.login.data.GeneralRepository

class SignUpViewModel (private val repository: GeneralRepository) : ViewModel() {
    fun signUp(name: String, email: String, phone: String, password: String, confPassword: String) = repository.signUp(name, email, phone, password, confPassword)
}