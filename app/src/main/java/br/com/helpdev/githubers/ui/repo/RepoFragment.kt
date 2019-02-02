package br.com.helpdev.githubers.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentRepoBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class RepoFragment : InjectableBindableFragment<FragmentRepoBinding>() {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentRepoBinding) {
    }
}
