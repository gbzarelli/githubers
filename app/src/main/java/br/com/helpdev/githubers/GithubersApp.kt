package br.com.helpdev.githubers

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import br.com.helpdev.githubers.di.AppInjector
import br.com.helpdev.githubers.di.worker.WorkerInjectorFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasContentProviderInjector
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
class GithubersApp : Application(),
    HasActivityInjector,
    HasContentProviderInjector {

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
     * Cria uma instancia para de AndroidInjector.
     * Responsável em injetar as dependencias mapeadas de forma automatica nos Providers.
     *
     * Creates an instance of AndroidInjector.
     * Responsible for injecting dependencies mapped automatically into the providers.
     */
    @Inject
    lateinit var dispatchingContentProviderInjector: DispatchingAndroidInjector<ContentProvider>

    /**
     * Injeta o WorkerInjectorFactory para configurar o WorkManager
     * Inject the WorkerInjectorFactory to configure WorkManager
     */
    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory

    override fun onCreate() {
        super.onCreate()
        configureWorkerWithDagger()
    }


    /**
     * Set the base context for this ContextWrapper.  All calls will then be
     * delegated to the base context.  Throws
     * IllegalStateException if a base context has already been set.
     *
     * @param base The new base context for this wrapper.
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        /**
         * Init DI to the Activities and Fragments
         *
         * Foi inicializado aqui por causa do Inject do ContentProvider, por algum motivo,
         * ele acaba não iniciando o dispacher no momento que o programa executa. Quando
         * o Provider chama o onCreate o Inject acaba pegando uma instancia nulla do Dispacher
         *
         * <https://stackoverflow.com/questions/23521083/inject-database-in-a-contentprovider-with-dagger>
         */
        AppInjector.init(this)
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

    /**
     * HasProviderInjector overrider; return the dispatching injector
     */
    override fun contentProviderInjector() = dispatchingContentProviderInjector
}