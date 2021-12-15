package com.nasa.apod.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NO_INTERNET
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> nointernet(message: String, data: T? = null): Resource<T> {
            return Resource(Status.NO_INTERNET, data, message)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}