package br.com.helpdev.githubers.data.repositories

import androidx.room.Dao
import br.com.helpdev.githubers.data.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(usersDao: UserDao) {
    fun getX() = "X"
}