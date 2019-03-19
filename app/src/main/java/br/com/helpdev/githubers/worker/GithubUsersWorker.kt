package br.com.helpdev.githubers.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.helpdev.githubers.data.db.entity.User
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import br.com.helpdev.githubers.di.worker.IWorkerFactory
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Provider

class GithubUsersWorker(
    context: Context, workerParams: WorkerParameters,
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "GithubUsersWorker"
        const val DATA_INT_LAST_ID = "last_id"
        const val DATA_LOAD_ONLY_USER = "login"
        const val DATA_BOOL_SAVE_IN_FAVORITES = "DATA_SAVE_IN_FAVORITES"
    }

    private val handler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, "GithubUsersWorker+CoroutineExceptionHandler", ex)
    }

    init {
        coroutineContext.plus(handler)
    }

    override suspend fun doWork(): Result {
        inputData.getString(DATA_LOAD_ONLY_USER)?.let { login ->
            val user = userRepository.loadUser(login, false)
            if (inputData.getBoolean(DATA_BOOL_SAVE_IN_FAVORITES, false) && user is User) {
                favoriteRepository.addFavorite(user.id)
            }
        } ?: userRepository.loadUserList(inputData.getInt(DATA_INT_LAST_ID, 0), false)
        return Result.success()
    }

    class Factory @Inject constructor(
        private val userRepository: Provider<UserRepository>,
        private val favoriteRepository: Provider<FavoriteRepository>
    ) : IWorkerFactory<GithubUsersWorker> {
        override fun create(appContext: Context, params: WorkerParameters): GithubUsersWorker {
            return GithubUsersWorker(appContext, params, userRepository.get(), favoriteRepository.get())
        }
    }

}