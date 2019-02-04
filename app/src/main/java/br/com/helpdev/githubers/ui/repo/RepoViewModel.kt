package br.com.helpdev.githubers.ui.repo

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class RepoViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {


}