package br.com.helpdev.githubers.ui.frags.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var userList: LiveData<PagedList<UserWithFav>>? = null

    fun getUserWithFavList(): LiveData<PagedList<UserWithFav>> {
        return userList ?: userRepository.getUsers(coroutineScope).also { userList = it }
    }

    fun addFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.addFavorite(id)
        }
    }

    fun removeFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.removeFavorite(id)
        }
    }

    fun getNetworkServiceStatus() =
        userRepository.getNetworkServiceStatus(UserRepository.LOAD_SERVICE_USERS)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}