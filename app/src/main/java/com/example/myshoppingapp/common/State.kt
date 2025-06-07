package com.example.myshoppingapp.common

sealed class State<out T> {

    data class Success<out T>(val data: T) : State<T>()
    data class Error(val error: String): State<Nothing>()
    object Loading : State<Nothing>()
}