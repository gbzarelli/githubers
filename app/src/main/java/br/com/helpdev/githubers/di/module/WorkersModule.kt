package br.com.helpdev.githubers.di.module

import androidx.work.ListenableWorker
import br.com.helpdev.githubers.di.worker.IWorkerFactory
import br.com.helpdev.githubers.di.worker.WorkerKey
import br.com.helpdev.githubers.worker.GithubUsersWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Modulo to the Workers register
 */
@Suppress("unused")
@Module
interface WorkersModule {

    /**********************************************
     * ADICIONAR OS WORKERS QUE UTILIZAM DI AQUI!
     * ADD THE WORKERS WITH DI USAGE HERE!
     *********************************************/

    @Binds
    @IntoMap
    @WorkerKey(GithubUsersWorker::class)
    fun bindGithubUsersWorker(worker: GithubUsersWorker.Factory): IWorkerFactory<out ListenableWorker>

}