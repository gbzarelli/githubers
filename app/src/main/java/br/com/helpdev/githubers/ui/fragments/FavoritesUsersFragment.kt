package br.com.helpdev.githubers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesUsersBinding.inflate(inflater, container, false)
        subscribeUI(binding)
        return binding.root
    }

    private fun subscribeUI(binding: FragmentFavoritesUsersBinding) {
        TODO("IMPLEMENT $binding")
    }
}
