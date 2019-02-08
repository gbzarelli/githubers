package br.com.helpdev.githubers

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import br.com.helpdev.githubers.di.AppInjector
import br.com.helpdev.githubers.di.worker.WorkerInjectorFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Classe Application do projeto. / Class Project application
 *
 * A implementação HasActivityInjector é utilizada para injetar dependências mapeadas
 * de forma automatica nas activities.
 *
 * The HasActivityInjector implementation is used to dependency injection mapped
 * automatically into the activities.
 *
 */
class GithubersApp : Application(), HasActivityInjector {

    /**
     * Cria uma instancia para de AndroidInjector.
     * Responsável em injetar as dependencias mapeadas de forma automatica nas activities.
     *
     * Creates an instance of AndroidInjector.
     * Responsible for injecting dependencies mapped automatically into the activities.
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    /**
     * Injeta o WorkerInjectorFactory para configurar o WorkManager
     * Inject the WorkerInjectorFactory to configure WorkManager
     */
    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory

    override fun onCreate() {
        super.onCreate()
        /**
         * Init DI to the Activities and Fragments
         */
        AppInjector.init(this)

        configureWorkerWithDagger()
    }

    /**
     * Configura o WorkManager para utilizar um Factory com DI
     * Configure the WorkManager to use a Factory with DI
     */
    private fun configureWorkerWithDagger() = WorkManager.initialize(
        this, Configuration.Builder()
            .setWorkerFactory(workerInjectorFactory)
            .build()
    )

    /**
     * HasActivityInjector overrider; return the dispatching injector
     */
    override fun activityInjector() = dispatchingAndroidInjector

}