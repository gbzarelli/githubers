package br.com.helpdev.githubers.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.db.dao.FavoriteDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.UserWithFav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(var userDao: FavoriteDao) {

    fun getFavUsers(): LiveData<PagedList<UserWithFav>> = LivePagedListBuilder(userDao.loadFavorites(), 50).build()

    suspend fun addFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.insertFavorite(FavUser(id))
        }
    }

    suspend fun removeFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userDao.removeFavorite(FavUser(id))
        }
    }
}