package br.com.helpdev.githubers.data.repository

import androidx.lifecycle.LiveData
import br.com.helpdev.githubers.data.db.dao.FavoriteDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.UserWithFav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(var userDao: FavoriteDao) {

    fun getFavUsers(): LiveData<List<UserWithFav>> = userDao.loadFavorites()

    suspend fun addToFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.insertFavorite(FavUser(id))
        }
    }

    suspend fun removeToFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.removeFavorite(FavUser(id))
        }
    }
}