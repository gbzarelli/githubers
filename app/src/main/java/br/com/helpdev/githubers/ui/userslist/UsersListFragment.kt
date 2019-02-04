package br.com.helpdev.githubers.ui.userslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentUsersListBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class UsersListFragment :
    InjectableBindingFragment<FragmentUsersListBinding, UsersListViewModel>(UsersListViewModel::class.java) {

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentUsersListBinding.inflate(inflater, container, false)


    override fun subscribeUI(
        viewModel: UsersListViewModel,
        binding: FragmentUsersListBinding,
        savedInstanceState: Bundle?
    ) {
    }
}