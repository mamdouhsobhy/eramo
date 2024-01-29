package com.example.eramo.core.presentation.base

sealed class BaseResult <out T> {
    data class DataState<T: Any>( val items: T?): BaseResult<T>()
    data class ErrorState(val code: String, val message: String): BaseResult<Nothing>()
}