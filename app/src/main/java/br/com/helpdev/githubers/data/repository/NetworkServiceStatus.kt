package br.com.helpdev.githubers.data.repository

/**
 * Classe para controle do status de rede
 * Class for network status control
 */
class NetworkServiceStatus(var status: Int = STATUS_NULL, var exception: Throwable? = null) {
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