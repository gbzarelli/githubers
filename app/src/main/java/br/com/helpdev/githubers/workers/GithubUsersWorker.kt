package br.com.helpdev.githubers.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

internal class GithubUsersWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val TAG by lazy { GithubUsersWorker::class.java.simpleName }

    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}