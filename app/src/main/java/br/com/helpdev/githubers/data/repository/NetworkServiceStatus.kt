package br.com.helpdev.githubers.data.repository

class NetworkServiceStatus(var status: Int = STATUS_FETCHING, var exception: Throwable? = null) {
    companion object {
        const val STATUS_NULL = 0
        const val STATUS_FETCHING = 1
        const val STATUS_SUCCESS = 2
        const val STATUS_ERROR = 3
    }

    override fun toString(): String {
        return "NetworkServiceStatus(status=$status, exception=$exception)"
    }

}