package br.com.helpdev.githubers.ui.frags.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersListViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var userList: LiveData<List<User>>? = null

    fun getUserList(): LiveData<List<User>> {
        return userList ?: githubUserRepository.getUserList(coroutineScope).also { userList = it }
    }

    fun loadUserListRemoteRepo() {
        githubUserRepository.loadUserListRemoteRepo(coroutineScope)
    }

    fun addToFavorite(id: Int) {
        coroutineScope.launch {
            githubUserRepository.addToFavorite(id)
        }
    }

    fun clearUserCache() {
        userList = null
    }

    fun getNetworkServiceStatus() =
        githubUserRepository.getNetworkServiceStatus(GithubUserRepository.LOAD_SERVICE_USERS)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}