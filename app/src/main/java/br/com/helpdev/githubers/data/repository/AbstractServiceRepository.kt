package br.com.helpdev.githubers.data.repository

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * Abstract repository with access control to external services.
 *
 * Provides an observable map on the network status for each service. Also provides the doCallService in Corroutine
 * with status control.
 */
abstract class AbstractServiceRepository {
    companion object {
        private val TAG by lazy { AbstractServiceRepository::class.java.simpleName }
    }

    private val networkServiceStatus: HashMap<Int, MutableLiveData<NetworkServiceStatus>> by lazy {
        HashMap<Int, MutableLiveData<NetworkServiceStatus>>()
    }

    fun getNetworkServiceStatus(id: Int): MutableLiveData<NetworkServiceStatus> = networkServiceStatus.getOrPut(id) {
        MutableLiveData()
    }

    /**
     * Method for calling services. Always use it for NetworkServiceStatus to be monitored.
     */
    internal suspend fun callMonitoredService(id: Int, params: Bundle? = null): Any? {
        getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_FETCHING)
        try {
            return doCallService(id, params).also {
                getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_SUCCESS)
            }
        } catch (e: Throwable) {
            getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_ERROR, e)
            Log.e(TAG, "callMonitoredService-Throwable", e)
        }
        return null
    }

    @Throws(Throwable::class)
    abstract suspend fun doCallService(id: Int, params: Bundle? = null): Any?
}
