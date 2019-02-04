package br.com.helpdev.githubers.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentRepoBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class RepoFragment : InjectableBindingFragment<FragmentRepoBinding, RepoViewModel>(RepoViewModel::class.java) {

    override fun binding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRepoBinding.inflate(inflater, container, false)


    override fun subscribeUI(viewModel: RepoViewModel, binding: FragmentRepoBinding, savedInstanceState: Bundle?) {
    }
}
