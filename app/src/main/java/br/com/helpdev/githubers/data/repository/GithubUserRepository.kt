package br.com.helpdev.githubers.data.repository

import androidx.lifecycle.LiveData
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(var userDao: UserDao, var githubService: GithubService) :
    AbstractServiceRepository() {

    companion object {
        const val LOAD_SERVICE_USERS = 1
    }

    fun getUserList(coroutineScope: CoroutineScope?): LiveData<List<User>> {
        coroutineScope?.launch { loadFromService(LOAD_SERVICE_USERS) }
        return userDao.load()
    }

    fun getFavUsers(): LiveData<List<User>> = userDao.loadFavorites()

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


    suspend fun addToFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.insertFavorite(FavUser(id))
        }
    }

    suspend fun removeToFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.insertFavorite(FavUser(id))
        }
    }
}