package br.com.helpdev.githubers.ui.frags.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var login: String = ""
    lateinit var user: LiveData<UserWithFav>

    fun init(login: String) {
        this.login = login
        user = userRepository.getUser(coroutineScope, login)
    }

    fun getNetworkServiceStatus() =
        userRepository.getNetworkServiceStatus(UserRepository.LOAD_SERVICE_USER)

    fun addToFavorite() {
        user.value ?: throw IllegalStateException("Init not called")
        coroutineScope.launch {
            favoriteRepository.addToFavorite(user.value!!.user.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}