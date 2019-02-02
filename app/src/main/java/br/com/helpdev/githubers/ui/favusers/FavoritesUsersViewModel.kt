package br.com.helpdev.githubers.ui.favusers

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class FavoritesUsersViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    fun getX() = githubUserRepository.getX()

}