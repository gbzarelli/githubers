package br.com.helpdev.githubers.ui.repolist

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {


}