package br.com.helpdev.githubers.ui.favrepos

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import javax.inject.Inject

class FavoritesReposViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    fun getX() = githubUserRepository.getX()

}