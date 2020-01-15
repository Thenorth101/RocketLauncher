package com.example.domain.entities

sealed class ResultState<T> {
    class Loading<T> : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val error: Throwable) : ResultState<T>()
}