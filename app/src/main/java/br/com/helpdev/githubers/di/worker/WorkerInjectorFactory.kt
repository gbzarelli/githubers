package br.com.helpdev.githubers.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

/**
 * O WorkerInjectorFactory é responsável por instanciar o {@link IWorkerFactory} do Worker.
 * Ele injeta as dependências dentro do {@link IWorkerFactory} que gera o Worker
 *
 * Obs: Lembre-se de mapear seu Worker em um modulo com o {@link WorkerKey}.
 *
 * The WorkerInjectorFactory is responsible to instancing the {@link IWorkerFactory} of the Worker.
 * It inject the dependencies into the {@link IWorkerFactory} that create the Worker.
 *
 * Note: Remember to map your Worker into a module with the {@link WorkerKey}.
 */
class WorkerInjectorFactory @Inject constructor(
    private val workerFactoryMap: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<IWorkerFactory<out ListenableWorker>>>
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters)
            : ListenableWorker? {

        val entry = workerFactoryMap.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }

        val factory = entry?.value ?: throw IllegalArgumentException("could not find worker: $workerClassName")

        return factory.get().create(appContext, workerParameters)
    }
}