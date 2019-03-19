package br.com.helpdev.githubers.ui.frags.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private var login: String = ""
    lateinit var user: LiveData<UserWithFav>

    fun init(login: String) {
        this.login = login
        user = userRepository.getUser(viewModelScope, login)
    }

    fun getNetworkServiceStatus() =
        userRepository.getNetworkServiceStatus(UserRepository.LOAD_SERVICE_USER)

    fun addToFavorite() {
        user.value ?: throw IllegalStateException("Init not called")
        viewModelScope.launch {
            favoriteRepository.addFavorite(user.value!!.user.id)
        }
    }

}