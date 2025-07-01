package com.example.booklibrary.data.model

sealed class DataState<out T> {
    data class Loading(val isLoading: Boolean) : DataState<Nothing>()
    data class Loaded<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Throwable) : DataState<Nothing>()
}