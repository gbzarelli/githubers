package br.com.helpdev.githubers.worker

import androidx.work.*

fun dispatchListUsersWorker(lastId: Int = 0) {
    dispatchUsersWorker(Data.Builder().apply { putInt(GithubUsersWorker.DATA_INT_LAST_ID, lastId) })
}

fun dispatchUserWorker(login: String) {
    dispatchUsersWorker(Data.Builder().apply { putString(GithubUsersWorker.DATA_LOAD_ONLY_USER, login) })
}

/**
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