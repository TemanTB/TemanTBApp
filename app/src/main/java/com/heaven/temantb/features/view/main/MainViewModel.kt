package com.heaven.temantb.features.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heaven.temantb.features.data.GeneralRepository
import com.heaven.temantb.features.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: GeneralRepository) : ViewModel() {

//    fun getStories(token: String) = repository.getStories(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getPointHealth(token:String, userId: String) = repository.getPointHealth(token, userId)


//    fun getPagedStories(token: String): LiveData<PagingData<ListStoryItem>> = repository.getStoryPaging(token).cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}