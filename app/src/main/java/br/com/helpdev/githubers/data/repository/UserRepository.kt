package br.com.helpdev.githubers.data.repository

import android.database.Cursor
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.db.entity.User
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.boundary.UserBoundaryCallback
import br.com.helpdev.githubers.worker.dispatchListUsersWorker
import br.com.helpdev.githubers.worker.dispatchUserWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var userDao: UserDao, var githubService: GithubService) :
    AbstractServiceRepository() {

    companion object {
        internal val TAG by lazy { UserRepository::class.java.simpleName }

        const val LOAD_SERVICE_USERS = 1
        const val LOAD_SERVICE_USER = 2
        const val DATABASE_PAGE_SIZE = 20

        private const val CALL_PARAM_LOGIN = "login"
        private const val CALL_PARAM_LAST_ID = "lastId"
        private const val CALL_PARAM_REDISPATCH = "redispatch"
    }

    fun getUsers(coroutineScope: CoroutineScope): LiveData<PagedList<UserWithFav>> {
        return LivePagedListBuilder(userDao.loadWithFav(), DATABASE_PAGE_SIZE)
            .setBoundaryCallback(UserBoundaryCallback(coroutineScope, this))
            .build()
    }

    fun getUser(coroutineScope: CoroutineScope, login: String): LiveData<UserWithFav> {
        coroutineScope.launch {
            val userWithFav = withContext(IO) { userDao.loadWithFavInstant(login) }
            if (userWithFav == null || userWithFav.isNeedUpdate()) {
                loadUser(login)
            }
        }
        return userDao.loadWithFav(login)
    }

    suspend fun loadUserList(lastId: Int = 0, redispatch: Boolean = true): Any? {
        return callMonitoredService(LOAD_SERVICE_USERS, Bundle().apply {
            putInt(CALL_PARAM_LAST_ID, lastId)
            putBoolean(CALL_PARAM_REDISPATCH, redispatch)
        })
    }

    suspend fun loadUser(login: String, redispatch: Boolean = true): User? {
        return callMonitoredService(LOAD_SERVICE_USER, Bundle().apply {
            putString(CALL_PARAM_LOGIN, login)
            putBoolean(CALL_PARAM_REDISPATCH, redispatch)
        }) as? User
    }

    override suspend fun doCallService(id: Int, params: Bundle?): Any? {
        if (LOAD_SERVICE_USERS == id) {
            callListUsersFromService(params)
        } else if (LOAD_SERVICE_USER == id) {
            callUserFromService(params)
        }
        return null
    }

    private suspend fun callListUsersFromService(params: Bundle?): List<User>? {
        val lastId = params?.getInt(CALL_PARAM_LAST_ID) ?: 0
        try {
            val response = githubService.listUsers(lastId).await()
            if (response.isSuccessful) return response.body()?.also { saveUsers(it) }
        } catch (ex: Throwable) {
            if (ex is IOException && params?.getBoolean(CALL_PARAM_REDISPATCH) == true)
                dispatchListUsersWorker(lastId)
            throw ex
        }
        return null
    }

    private suspend fun callUserFromService(params: Bundle?): User? {
        val login = params?.getString(CALL_PARAM_LOGIN) ?: ""
        try {
            val response = githubService.getUser(login).await()
            if (response.isSuccessful) return response.body()?.also { saveUser(it) }
        } catch (ex: Throwable) {
            if (ex is IOException && params?.getBoolean(CALL_PARAM_REDISPATCH) == true)
                dispatchUserWorker(login)
            throw ex
        }
        return null
    }

    private suspend fun saveUsers(user: List<User>) {
        withContext(IO) { userDao.save(user) }
    }

    private suspend fun saveUser(user: User) {
        withContext(IO) { userDao.save(user) }
    }

    /**
     * Find the information of users as search suggestions
     * The limit is used to limit the register quantity and to realize a validation where,
     * if the returned data of the database is lower than the limit, a search will be carried
     * that fill the database and the new values will be returned
     *
     * @return Cursor - retorna '_id' e 'suggest_text_1' - Baseado no SearchManager
     */
    fun findLoginSuggestionsSynchronous(query: String, limit: Int): Cursor? {
        return userDao.findLoginSuggestion(query, limit).let { cursor ->
            return@let if (cursor.count < limit) {
                findLoginSuggestionsFromService(query, limit)
            } else {
                cursor
            }
        }
    }

    private fun findLoginSuggestionsFromService(query: String, limit: Int): Cursor? {
        return githubService.findUsers(query).execute().body()?.let { searchUsers ->
            userDao.save(searchUsers.items)
            userDao.findLoginSuggestion(query, limit)
        }
    }

    /**
     * @return Cursor - retorna '_id' e 'suggest_text_1' - Baseado no SearchManager
     */
    fun findLoginSuggestionsSynchronous(login: String) = userDao.findLoginSuggestion(login)

}