package br.com.helpdev.githubers.ui.util

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.repository.NetworkServiceStatus
import br.com.helpdev.githubers.databinding.LoadNetworkBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import com.google.android.material.snackbar.Snackbar

object NetworkBindingUtil {

    fun monitorServiceStatus(
        owner: LifecycleOwner,
        networkServiceStatus: MutableLiveData<NetworkServiceStatus>,
        layoutNetworkBinding: LoadNetworkBinding,
        hasElements: () -> Boolean,
        networkError: (Throwable?) -> Unit
    ) {

        networkServiceStatus.observe(owner, Observer {
            layoutNetworkBinding.isLoading = it.status == NetworkServiceStatus.STATUS_FETCHING

            if (hasElements()) {
                if (layoutNetworkBinding.noDataAndDoNotLoading) layoutNetworkBinding.noDataAndDoNotLoading = false
            } else {
                layoutNetworkBinding.noDataAndDoNotLoading = !layoutNetworkBinding.isLoading
            }

            when (it.status) {
                NetworkServiceStatus.STATUS_ERROR -> {
                    networkError(it.exception)
                }
            }
        })
    }

    fun setDataReached(layoutNetworkBinding: LoadNetworkBinding) {
        if (layoutNetworkBinding.noDataAndDoNotLoading) layoutNetworkBinding.noDataAndDoNotLoading = false
    }


    fun showSnackNetworkError(context: Context, view: View) {
        showSnackError(view, context.getString(R.string.verify_conection))
    }

    fun showSnackError(view: View, message: String) {
        try {
            Snackbar.make(
                view, message,
                Snackbar.LENGTH_LONG
            ).setActionTextColor(Color.GRAY)
                .setAction(R.string.dismiss) { }.show()
        } catch (th: Throwable) {
            Log.e(InjectableBindingFragment.TAG, "showSnackError", th)
        }
    }

}