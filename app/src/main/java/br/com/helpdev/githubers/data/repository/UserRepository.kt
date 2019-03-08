package br.com.helpdev.githubers.data.repository

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.work.*
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.boundary.UserBoundaryCallback
import br.com.helpdev.githubers.worker.GithubUsersWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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

    fun getUserWithFavList(coroutineScope: CoroutineScope): LiveData<PagedList<UserWithFav>> {
        return LivePagedListBuilder(userDao.loadWithFav(), DATABASE_PAGE_SIZE)
            .setBoundaryCallback(UserBoundaryCallback(coroutineScope, this))
            .build()
    }

    suspend fun loadUserListRemoteRepo(lastId: Int = 0, redispatch: Boolean = true): Any? {
        return loadFromService(LOAD_SERVICE_USERS, Bundle().apply {
            putInt(CALL_PARAM_LAST_ID, lastId)
            putBoolean(CALL_PARAM_REDISPATCH, redispatch)
        })
    }

    suspend fun loadUserRemoteRepo(login: String, redispatch: Boolean = true): User? {
        return loadFromService(LOAD_SERVICE_USER, Bundle().apply {
            putString(CALL_PARAM_LOGIN, login)
            putBoolean(CALL_PARAM_REDISPATCH, redispatch)
        }) as? User
    }

    override suspend fun call(id: Int, params: Bundle?): Any? {
        if (LOAD_SERVICE_USERS == id) {
            val lastId = params?.getInt(CALL_PARAM_LAST_ID) ?: 0
            try {
                val await = githubService.listUsers(lastId).await()
                if (await.isSuccessful)
                    return await.body()?.also { saveUsers(it) }
            } catch (ex: Throwable) {
                if (ex is IOException && params?.getBoolean(CALL_PARAM_REDISPATCH) == true) dispatchListUsersWorker(
                    lastId
                )
                throw ex
            }
        } else if (LOAD_SERVICE_USER == id) {
            val login = params?.getString(CALL_PARAM_LOGIN) ?: ""
            try {
                val await = githubService.getUser(login).await()
                if (await.isSuccessful)
                    return await.body()?.also { saveUser(it) }
            } catch (ex: Throwable) {
                if (ex is IOException && params?.getBoolean(CALL_PARAM_REDISPATCH) == true) dispatchUserWorker(login)
                throw ex
            }
        }
        return null
    }

    private fun dispatchListUsersWorker(lastId: Int = 0) {
        dispatchUsersWorker(Data.Builder().apply { putInt(GithubUsersWorker.DATA_INT_LAST_ID, lastId) })
    }

    private fun dispatchUserWorker(login: String) {
        dispatchUsersWorker(Data.Builder().apply { putString(GithubUsersWorker.DATA_LOAD_ONLY_USER, login) })
    }

    /**
     * Enfileira um {@link GithubUsersWorker} para ser executado somente uma vez
     * quando houver conexão de rede.
     *
     * Enqueue a {@link GithubUsersWorker} for execute only one time
     * when has network connection
     */
    private fun dispatchUsersWorker(data: Data.Builder) {
        WorkManager.getInstance()
            .enqueueUniqueWork(
                GithubUsersWorker::class.java.simpleName,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequestBuilder<GithubUsersWorker>()
                    .setInputData(data.build())
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    ).build()
            )
    }

    private suspend fun saveUsers(user: List<User>) {
        withContext(IO) { userDao.save(user) }
    }

    private suspend fun saveUser(user: User) {
        withContext(IO) { userDao.save(user) }
    }

    fun getUserWithFav(coroutineScope: CoroutineScope, login: String): LiveData<UserWithFav> {
        coroutineScope.launch {
            val us = withContext(IO) { userDao.loadWithFavInstant(login) }
            us?.user?.let { user ->
                user.created_at?.let {
                    if ((System.currentTimeMillis() - user.registerDateTime!!.timeInMillis) < TimeUnit.HOURS.toMillis(1)) {
                        Log.i(TAG, "REGISTER UPDATED IN LESS THAN 1Hr. SEARCH IGNORED.")
                        return@launch
                    }
                }
            }
            loadUserRemoteRepo(login)
        }

        return userDao.loadWithFav(login)
    }

    /**
     * Busca informações de usuarios como sugestões de pesquisa.
     * O Limit é utilizado para limitar a quantidade de registro e para realizar
     * uma validação aonde, se os dados retornados do banco forem inferiores ao limit,
     * será realizado uma busca na API que popula a base e retorna os novos valores.
     *
     * Find the information of users as search suggestions
     * The limit is used to limit the register quantity and to realize a validation where,
     * if the returned data of the database is lower than the limit, a search will be carried
     * that fill the database and the new values will be returned
     *
     * @return Cursor - pode ser null ( retorna '_id' e 'suggest_text_1' - Baseado no SearchManager )
     */
    fun findLoginSuggestionSynchronous(query: String, limit: Int): Cursor? {
        return userDao.findLoginSuggestion(query, limit).let { cursor ->
            if (cursor.count < limit) {
                return@let githubService.findUsers(query).execute().body()?.let { searchUsers ->
                    userDao.save(searchUsers.items)
                    userDao.findLoginSuggestion(query, limit)
                }
            }
            cursor
        }
    }

    /**
     * Busca apenas um usuário como dados de sugestão baseado no seu user_id.
     * Search only a user as suggestion data based on your user_id
     *
     * @return Cursor - pode ser null ( retorna '_id' e 'suggest_text_1' - Baseado no SearchManager )
     */
    fun findLoginSuggestionSynchronous(userId: Int): Cursor? {
        return userDao.findLoginSuggestion(userId)
    }

}