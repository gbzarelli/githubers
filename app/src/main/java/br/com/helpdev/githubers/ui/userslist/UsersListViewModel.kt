package br.com.helpdev.githubers.ui.userslist

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class UsersListViewModel @Inject constructor(githubUserRepository: GithubUserRepository) : ViewModel() {

}