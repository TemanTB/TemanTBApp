package com.heaven.temantb.login.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heaven.temantb.login.data.GeneralRepository
import com.heaven.temantb.login.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: GeneralRepository) : ViewModel() {

//    fun getStories(token: String) = repository.getStories(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun getPagedStories(token: String): LiveData<PagingData<ListStoryItem>> = repository.getStoryPaging(token).cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}