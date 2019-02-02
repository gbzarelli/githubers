package br.com.helpdev.githubers.ui.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentRepoListBinding
import br.com.helpdev.githubers.ui.InjectableBindableFragment

/**
 * A placeholder fragment containing a simple view.
 */
class RepoListFragment : InjectableBindableFragment<FragmentRepoListBinding>() {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoListBinding.inflate(inflater, container, false)


    override fun subscribeUI(binding: FragmentRepoListBinding) {
    }

}
