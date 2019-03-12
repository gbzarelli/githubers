package br.com.helpdev.githubers.databinding

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.helpdev.githubers.data.repository.NetworkServiceStatus

fun LoadNetworkBinding.observerServiceStatus(
    owner: LifecycleOwner,
    networkServiceStatus: MutableLiveData<NetworkServiceStatus>,
    hasElements: () -> Boolean,
    networkError: (Throwable?) -> Unit
) {

    networkServiceStatus.observe(owner, Observer {
        isLoading = it.status == NetworkServiceStatus.STATUS_FETCHING

        if (it.status == NetworkServiceStatus.STATUS_SUCCESS || hasElements()) {
            setDataReached()
        } else {
            noDataAndDoNotLoading = !isLoading
        }

        when (it.status) {
            NetworkServiceStatus.STATUS_ERROR -> {
                networkError(it.exception)
            }
        }
    })
}

fun LoadNetworkBinding.setDataReached() {
    if (noDataAndDoNotLoading) noDataAndDoNotLoading = false
}

