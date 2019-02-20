package br.com.helpdev.githubers.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.helpdev.githubers.data.repository.UserRepository
import br.com.helpdev.githubers.di.worker.IWorkerFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 *
 */
class GithubUsersWorker(
    context: Context, workerParams: WorkerParameters,
    private val userRepository: UserRepository
) : Worker(context, workerParams) {
    companion object {
        const val TAG = "GithubUsersWorker"
        const val DATA_INT_LAST_ID = "last_id"
        const val DATA_LOAD_ONLY_USER = "login"
    }

    private val handler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, "GithubUsersWorker+CoroutineExceptionHandler", ex)
    }

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.Main + handler).launch {
            inputData.getString(DATA_LOAD_ONLY_USER)
                ?.run { userRepository.loadUserRemoteRepo(this, false) }
                ?: userRepository.loadUserListRemoteRepo(inputData.getInt(DATA_INT_LAST_ID, 0), false)
        }
        return Result.success()
    }

    class Factory @Inject constructor(
        private val userRepository: Provider<UserRepository>
    ) : IWorkerFactory<GithubUsersWorker> {
        override fun create(appContext: Context, params: WorkerParameters): GithubUsersWorker {
            return GithubUsersWorker(appContext, params, userRepository.get())
        }
    }

}