package br.com.helpdev.githubers.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.helpdev.githubers.data.repository.GithubUserRepository
import br.com.helpdev.githubers.di.worker.IWorkerFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject
import javax.inject.Provider


class GithubUsersWorker(
    context: Context, workerParams: WorkerParameters,
    private val userRepository: GithubUserRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        userRepository.loadUserListRemoteRepo(CoroutineScope(Dispatchers.Main))
        return Result.success()
    }

    class Factory @Inject constructor(
        private val userRepository: Provider<GithubUserRepository> // provide from Gson
    ) : IWorkerFactory<GithubUsersWorker> {
        override fun create(appContext: Context, params: WorkerParameters): GithubUsersWorker {
            return GithubUsersWorker(appContext, params, userRepository.get())
        }
    }

}