package br.com.helpdev.githubers.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentUsersListBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class UsersListFragment : InjectableBindableFragment<FragmentUsersListBinding>()  {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentUsersListBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentUsersListBinding) {
    }
}