package com.dtks.photosaroundme.utils

sealed class AsyncResource<out T> {
    object Loading : AsyncResource<Nothing>()

    data class Error(val errorMessage: Int) : AsyncResource<Nothing>()

    data class Success<out T>(val data: T) : AsyncResource<T>()
}