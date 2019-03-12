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

class GithubersApp : Application(),
    HasActivityInjector,
    HasContentProviderInjector {

    /**
     * Responsible for injecting dependencies mapped automatically into the activities.
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    /**
     * Responsible for injecting dependencies mapped automatically into the providers.
     */
    @Inject
    lateinit var dispatchingContentProviderInjector: DispatchingAndroidInjector<ContentProvider>

    /**
     * Inject the WorkerInjectorFactory to configure WorkManager
     */
    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory

    override fun onCreate() {
        super.onCreate()
        configureWorkerWithDagger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        /**
         * Foi inicializado aqui por causa do Inject do ContentProvider, por algum motivo,
         * ele acaba não iniciando o dispacher no momento que o programa executa. Quando
         * o Provider chama o onCreate o Inject acaba pegando uma instancia nulla do Dispacher
         *
         * <https://stackoverflow.com/questions/23521083/inject-database-in-a-contentprovider-with-dagger>
         */
        AppInjector.init(this)
    }

    private fun configureWorkerWithDagger() = WorkManager.initialize(
        this, Configuration.Builder()
            .setWorkerFactory(workerInjectorFactory)
            .build()
    )

    override fun activityInjector() = dispatchingAndroidInjector

    override fun contentProviderInjector() = dispatchingContentProviderInjector
}