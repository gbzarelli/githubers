package br.com.helpdev.githubers.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentUserBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class UserFragment : InjectableBindableFragment<FragmentUserBinding>() {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentUserBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentUserBinding) {
    }
}
