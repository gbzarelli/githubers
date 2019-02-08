package br.com.helpdev.githubers.data.repository

import androidx.lifecycle.LiveData
import br.com.helpdev.githubers.BuildConfig
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var userDao: UserDao, var githubService: GithubService) :
    AbstractServiceRepository() {

    companion object {
        const val LOAD_SERVICE_USERS = 1
    }

    fun getUserList(coroutineScope: CoroutineScope?): LiveData<List<User>> {
        coroutineScope?.let { loadUserListRemoteRepo(it) }
        return userDao.load()
    }

    fun loadUserListRemoteRepo(coroutineScope: CoroutineScope) {
        coroutineScope.launch { loadFromService(LOAD_SERVICE_USERS) }
    }

    override suspend fun call(id: Int) {
        if (LOAD_SERVICE_USERS == id) {
            githubService.listUsers().await().apply {
                if (isSuccessful) body()?.let { saveUsers(it) }
            }
        }
    }

    private suspend fun saveUsers(user: List<User>) {
        withContext(Dispatchers.IO) { userDao.save(user) }
    }

}