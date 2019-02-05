package br.com.helpdev.githubers.ui.favusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : InjectableBindingFragment<FragmentFavoritesUsersBinding, FavoritesUsersViewModel>
    (FavoritesUsersViewModel::class.java) {

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentFavoritesUsersBinding.inflate(inflater, container, false)

    override fun subscribeUI(
        viewModel: FavoritesUsersViewModel,
        binding: FragmentFavoritesUsersBinding,
        savedInstanceState: Bundle?
    ) {

        viewModel.getFavoriteUsersList().observe(this, Observer {
            Log.d(TAG, "OBSERVER getFavoriteUsersList: {$it}")
        })


        //TODO - load de favorite list with the viewModel

        /**
         * Ao clicar no FAB realiza a navegação para a UsersListFragment
         * Clicking the FAB navigates to the UsersListFragment
         */
        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUsersListFragment())
        }
    }
}


