package br.com.helpdev.githubers.data.repository

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * Repositorio abstrato com controle de acesso a serviços externos.
 *
 * Fornece um map observável sobre o status de rede para cada serviço. Também fornece a chamada em corroutine
 * com o controle de status.
 *
 *
 * Abstract repository with access control to external services.
 *
 * Provides an observable map on the network status for each service. Also provides the call in Corroutine
 * with status control.
 */
abstract class AbstractServiceRepository {
    companion object {
        private val TAG by lazy { AbstractServiceRepository::class.java.simpleName }
    }

    /**
     * HashMap para controle dos objetos observaveis de rede.
     * HashMap for controlling observable network objects
     */
    private val networkServiceStatus: HashMap<Int, MutableLiveData<NetworkServiceStatus>> by lazy {
        HashMap<Int, MutableLiveData<NetworkServiceStatus>>()
    }

    fun getNetworkServiceStatus(id: Int): MutableLiveData<NetworkServiceStatus> = networkServiceStatus.getOrPut(id) {
        MutableLiveData()
    }

    /**
     * Método para chamada de serviços. Sempre utiliza-lo para que o NetworkServiceStatus seja monitorado.
     *
     * Method for calling services. Always use it for NetworkServiceStatus to be monitored.
     */
    internal suspend fun loadFromService(id: Int, params: Bundle? = null): Any? {
        getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_FETCHING)
        try {
            return call(id, params).also {
                getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_SUCCESS)
            }
        } catch (e: Throwable) {
            getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_ERROR, e)
            Log.e(TAG, "loadFromService-Throwable", e)
        }
        return null
    }

    @Throws(Throwable::class)
    abstract suspend fun call(id: Int, params: Bundle? = null): Any?
}
