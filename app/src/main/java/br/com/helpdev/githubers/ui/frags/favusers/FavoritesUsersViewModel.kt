package br.com.helpdev.githubers.ui.frags.favusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FavoritesUsersViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var userList: LiveData<PagedList<UserWithFav>>? = null

    fun getFavoriteUsersList(): LiveData<PagedList<UserWithFav>> {
        return userList ?: favoriteRepository.getFavUsers().also { userList = it }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun removeFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.removeFavorite(id)
        }
    }

    fun addFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.addFavorite(id)
        }
    }

}