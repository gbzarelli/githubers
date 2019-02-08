package br.com.helpdev.githubers.data.repository

import androidx.lifecycle.LiveData
import br.com.helpdev.githubers.data.db.dao.FavoriteDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(var userDao: FavoriteDao) {

    fun getFavUsers(): LiveData<List<User>> = userDao.loadFavorites()

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