package br.com.helpdev.githubers.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.HttpException

abstract class AbstractServiceRepository {
    companion object {
        private val TAG by lazy { AbstractServiceRepository::class.java.simpleName }
    }

    private val networkServiceStatus: HashMap<Int, MutableLiveData<NetworkServiceStatus>> by lazy {
        HashMap<Int, MutableLiveData<NetworkServiceStatus>>()
    }

    fun getNetworkServiceStatus(id: Int): MutableLiveData<NetworkServiceStatus> = networkServiceStatus.getOrPut(id) {
        MutableLiveData()
    }//.apply { value = NetworkServiceStatus(NetworkServiceStatus.STATUS_NULL) }


    suspend fun loadFromService(id: Int) {
        getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_FETCHING)
        try {
            call(id)
            getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_SUCCESS)
        } catch (e: HttpException) {
            getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_ERROR, e)
            Log.e(TAG, "loadFromService-HttpException", e)
        } catch (e: Throwable) {
            getNetworkServiceStatus(id).value = NetworkServiceStatus(NetworkServiceStatus.STATUS_ERROR, e)
            Log.e(TAG, "loadFromService-Throwable", e)
        }
    }

    @Throws(HttpException::class, Throwable::class)
    abstract suspend fun call(id: Int)
}
