package com.ajm.kotlin.mvvm.Utils

sealed class Resource<T> {
    class Loading<T> : Resource<T>()

    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error<T>(
        val errorMessage: String?
    ) : Resource<T>()
}
