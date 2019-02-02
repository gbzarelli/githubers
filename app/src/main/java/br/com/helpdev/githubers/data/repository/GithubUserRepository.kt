package br.com.helpdev.githubers.data.repository

import br.com.helpdev.githubers.data.db.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(usersDao: UserDao) {
    fun getX() = "X"
}