package br.com.helpdev.githubers.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repositories.GithubUserRepository
import javax.inject.Inject

class FavoritesUsersListViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    fun getX() = githubUserRepository.getX()

}