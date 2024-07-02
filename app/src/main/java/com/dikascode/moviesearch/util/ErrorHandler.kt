package com.dikascode.moviesearch.util

import java.io.IOException

object ErrorHandler {
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is IOException -> "Network error, please check your connection."
            else -> "An unexpected error occurred. Please try again."
        }
    }
}

class NetworkException(message: String) : IOException(message)
class HttpException(val code: Int, message: String) : IOException(message)
class UnknownException(message: String) : Exception(message)