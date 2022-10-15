package com.jonathanmojica.examenandroid.utils

class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> stopLoader(data: T?) : Resource<T> {
            return Resource(Status.STOP, data, null)
        }

        fun <T> throwError(message: String?, data: T?) : Resource<T> {
            return Resource(Status.THROW, data, message)
        }

        fun <T> setReset(data : T?) : Resource<T> {
            return Resource(Status.RESET, data, null)
        }
    }
}