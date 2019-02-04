package br.com.helpdev.githubers.ui.user

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

}