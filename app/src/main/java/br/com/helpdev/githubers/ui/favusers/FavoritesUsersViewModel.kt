package br.com.helpdev.githubers.ui.favusers

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FavoritesUsersViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    fun getUserList() = githubUserRepository.getUserList(coroutineScope)

    fun getNetworkServiceStatus() =
        githubUserRepository.getNetworkServiceStatus(GithubUserRepository.LOAD_SERVICE_USERS)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}