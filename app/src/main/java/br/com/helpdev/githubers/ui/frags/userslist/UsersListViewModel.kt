package br.com.helpdev.githubers.ui.frags.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private var userList: LiveData<PagedList<UserWithFav>>? = null

    fun getUserWithFavList(): LiveData<PagedList<UserWithFav>> {
        return userList ?: userRepository.getUsers(viewModelScope).also { userList = it }
    }

    fun addFavorite(id: Int) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(id)
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(id)
        }
    }

    fun getNetworkServiceStatus() =
        userRepository.getNetworkServiceStatus(UserRepository.LOAD_SERVICE_USERS)

}