package br.com.helpdev.githubers.ui.favrepos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import br.com.helpdev.githubers.databinding.FragmentFavoritesReposBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesReposFragment : InjectableBindableFragment<FragmentFavoritesReposBinding>() {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFavoritesReposBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentFavoritesReposBinding) {
        val viewModel = ViewModelProviders.of(this, viewModelInjectFactory).get(FavoritesReposViewModel::class.java)
        Toast.makeText(activity, "X: {${viewModel.getX()}}", Toast.LENGTH_LONG).show()
    }
}


