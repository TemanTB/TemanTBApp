package com.heaven.temantb.login.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heaven.temantb.login.data.UserRepository
import com.heaven.temantb.login.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}