package br.com.helpdev.githubers.ui.frags.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.databinding.FragmentRepoListBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment

/**
 * A placeholder fragment containing a simple view.
 */
class RepoListFragment : InjectableBindingFragment<FragmentRepoListBinding, RepoListViewModel>
    (RepoListViewModel::class.java) {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRepoListBinding.inflate(inflater, container, false)


    override fun subscribeUI(
        viewModel: RepoListViewModel,
        binding: FragmentRepoListBinding,
        savedInstanceState: Bundle?
    ) {
    }

}
