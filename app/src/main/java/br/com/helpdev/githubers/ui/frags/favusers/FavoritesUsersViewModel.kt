package br.com.helpdev.githubers.ui.frags.favusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesUsersViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private var userList: LiveData<PagedList<UserWithFav>>? = null

    fun getFavoriteUsersList(): LiveData<PagedList<UserWithFav>> {
        return userList ?: favoriteRepository.getFavUsers().also { userList = it }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(id)
        }
    }

    fun addFavorite(id: Int) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(id)
        }
    }

}