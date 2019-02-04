package br.com.helpdev.githubers.ui.favusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : InjectableBindingFragment<FragmentFavoritesUsersBinding, FavoritesUsersViewModel>
    (FavoritesUsersViewModel::class.java) {

    companion object {
        private val TAG by lazy { FavoritesUsersFragment::class.java.simpleName }
    }

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentFavoritesUsersBinding.inflate(inflater, container, false)

    override fun subscribeUI(
        viewModel: FavoritesUsersViewModel,
        binding: FragmentFavoritesUsersBinding,
        savedInstanceState: Bundle?
    ) {

        viewModel.getNetworkServiceStatus().observe(this, Observer {
            Log.d(TAG, "OBSERVER getNetworkServiceStatus: {$it}")
        })

        viewModel.getUserList().observe(this, Observer {
            Log.d(TAG, "OBSERVER getUserList: {$it}")
        })
    }
}


