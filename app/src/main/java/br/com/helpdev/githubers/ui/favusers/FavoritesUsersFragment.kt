package br.com.helpdev.githubers.ui.favusers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : InjectableBindableFragment<FragmentFavoritesUsersBinding>() {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFavoritesUsersBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentFavoritesUsersBinding) {
        val viewModel = ViewModelProviders.of(this, viewModelInjectFactory).get(FavoritesUsersViewModel::class.java)
        Toast.makeText(activity, "X: {${viewModel.getX()}}", Toast.LENGTH_LONG).show()
    }
}


