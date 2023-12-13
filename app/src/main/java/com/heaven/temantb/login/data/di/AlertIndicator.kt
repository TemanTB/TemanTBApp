package com.heaven.temantb.login.data.di

sealed class AlertIndicator<out R> private constructor() {
    data class Success<out T>(val data: T) : AlertIndicator<T>()
    data class Error(val error: String) : AlertIndicator<Nothing>()
    data object Loading : AlertIndicator<Nothing>()
}