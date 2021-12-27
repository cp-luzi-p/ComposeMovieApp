package com.example.composemovieapp.data


data class Source<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> empty(): Source<T> {
            return Source(Status.EMPTY, null,  null)
        }

        fun <T> success(data: T?): Source<T> {
            return Source(Status.SUCCESS, data, null)
        }

        fun <T> error(msg:String, data: T?): Source<T> {
            return Source(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Source<T> {
            return Source(Status.LOADING, data, null)
        }
    }
}


