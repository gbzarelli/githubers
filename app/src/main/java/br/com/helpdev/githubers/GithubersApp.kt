package br.com.helpdev.githubers

import android.app.Activity
import android.app.Application
import br.com.helpdev.githubers.di.AppInjector
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

    override fun onCreate() {
        super.onCreate()
        /**
         * Inicia a DI.
         */
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}