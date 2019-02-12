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
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var userDao: UserDao, var githubService: GithubService) :
    AbstractServiceRepository() {

    companion object {
        const val LOAD_SERVICE_USERS = 1
        const val DATABASE_PAGE_SIZE = 20
    }

    fun getUserWithFavList(): LiveData<PagedList<UserWithFav>> {
        return LivePagedListBuilder(userDao.loadWithFav(), DATABASE_PAGE_SIZE)
            .setBoundaryCallback(UserBoundaryCallback(this))
            .build()
    }

    fun loadUserListRemoteRepo(coroutineScope: CoroutineScope, lastId: Int = 0) {
        coroutineScope.launch {
            loadFromService(LOAD_SERVICE_USERS, Bundle().apply { putInt("lastId", lastId) })
        }
    }

    override suspend fun call(id: Int, params: Bundle?) {
        if (LOAD_SERVICE_USERS == id) {
            val lastId = params?.getInt("lastId") ?: 0
            try {
                githubService.listUsers(lastId)
                    .await()
                    .apply {
                        if (isSuccessful) body()?.let {
                            saveUsers(it)
                        }
                    }
            } catch (ex: IOException) {// <-- Network Exception?! timeout/unknown hostname/etc
                dispatchUsersWorker(lastId)
                throw ex
            }
        }
    }

    /**
     * Enfileira um {@link GithubUsersWorker} para ser executado somente uma vez
     * quando houver conexão de rede.
     *
     * Enqueue a {@link GithubUsersWorker} for execute only one time
     * when has network connection
     */
    private fun dispatchUsersWorker(lastId: Int = 0) {
        val data = Data.Builder()
        data.putInt(GithubUsersWorker.DATA_INT_LAST_ID, lastId)

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

}