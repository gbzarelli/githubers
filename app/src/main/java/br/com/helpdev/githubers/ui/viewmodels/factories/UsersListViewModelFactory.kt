package br.com.helpdev.githubers.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.helpdev.githubers.data.repositories.GithubRepository
import br.com.helpdev.githubers.ui.viewmodels.UsersListViewModel

/**
 * O factory é necessário quando o ViewModel recebe parametros.
 * --
 * The factory is necessary when ViewModel receive parameters
 */
class UsersListViewModelFactory(private val githubRepository: GithubRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersListViewModel(githubRepository) as T
    }
}