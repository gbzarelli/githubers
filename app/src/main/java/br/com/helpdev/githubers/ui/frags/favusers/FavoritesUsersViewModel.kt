package br.com.helpdev.githubers.ui.frags.favusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FavoritesUsersViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var userList: LiveData<List<User>>? = null

    fun getFavoriteUsersList(): LiveData<List<User>> {
        return userList ?: githubUserRepository.getFavUsers().also { userList = it }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun addToFavorite(id: Int) {
        coroutineScope.launch {
            githubUserRepository.addToFavorite(id)
        }
    }

    fun removeToFavorite(id: Int) {
        coroutineScope.launch {
            githubUserRepository.removeToFavorite(id)
        }
    }


}