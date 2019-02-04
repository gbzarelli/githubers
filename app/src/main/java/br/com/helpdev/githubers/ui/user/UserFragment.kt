package br.com.helpdev.githubers.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentUserBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class UserFragment : InjectableBindingFragment<FragmentUserBinding, UserViewModel>
    (UserViewModel::class.java) {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentUserBinding.inflate(inflater, container, false)


    override fun subscribeUI(viewModel: UserViewModel, binding: FragmentUserBinding, savedInstanceState: Bundle?) {
    }
}
