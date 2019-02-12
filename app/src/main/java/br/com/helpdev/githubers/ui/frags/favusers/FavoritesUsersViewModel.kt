package br.com.helpdev.githubers.ui.frags.favusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FavoritesUsersViewModel @Inject constructor(
    private val ur: UserRepository,
    private val favoriteRepository: FavoriteRepository
) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var userList: LiveData<List<UserWithFav>>? = null

    fun getFavoriteUsersList(): LiveData<List<UserWithFav>> {
        return userList ?: favoriteRepository.getFavUsers().also { userList = it }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun addToFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.addToFavorite(id)
        }
    }

    fun removeFromFavorite(id: Int) {
        coroutineScope.launch {
            favoriteRepository.removeFavorite(id)
        }
    }


}