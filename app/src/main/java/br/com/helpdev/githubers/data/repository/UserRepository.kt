package br.com.helpdev.githubers.data.repository

import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var userDao: UserDao, var githubService: GithubService) :
    AbstractServiceRepository() {

    companion object {
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
        withContext(Dispatchers.IO) { userDao.save(user) }
    }

    private suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) { userDao.save(user) }
    }

    fun getUserWithFav(coroutineScope: CoroutineScope, login: String): LiveData<UserWithFav> {
        coroutineScope.launch { loadUserRemoteRepo(login) }
        return userDao.loadWithFav(login)
    }

}