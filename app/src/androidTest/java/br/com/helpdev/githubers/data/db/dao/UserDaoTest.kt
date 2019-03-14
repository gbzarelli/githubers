package br.com.helpdev.githubers.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.helpdev.githubers.JSON_USER
import br.com.helpdev.githubers.data.db.GithubDatabase
import br.com.helpdev.githubers.data.db.GithubDatabaseTest
import br.com.helpdev.githubers.data.db.entity.User
import br.com.helpdev.githubers.di.module.AppModule
import br.com.helpdev.githubers.util.getValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserDaoTest : GithubDatabaseTest() {

    lateinit var userDao: UserDao
    private val gson = AppModule().provideGson()
    lateinit var user: User

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun before() {
        super.before()
        loadUserFromJsonToTest()
    }

    override fun onDBCreated(githubDatabase: GithubDatabase) {
        super.onDBCreated(githubDatabase)
        userDao = githubDatabase.userDao()
    }

    fun loadUserFromJsonToTest() {
        user = gson.fromJson<User>(JSON_USER, User::class.java)
    }

    @Test
    fun testInsertNewUserWithSuccess() {
        userDao.save(user)
        val liveDataUser = userDao.load(user.id)
        Assert.assertEquals(getValue(liveDataUser).id, user.id)
    }

    @Test
    fun testDeleteUserWithSuccess() {
        testInsertNewUserWithSuccess()
        userDao.remove(user)
        val liveDataUser = userDao.load(user.id)
        Assert.assertNull(getValue(liveDataUser))
    }
}