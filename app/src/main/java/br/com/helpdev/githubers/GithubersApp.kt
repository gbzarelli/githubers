package br.com.helpdev.githubers

import android.app.Activity
import android.app.Application
import br.com.helpdev.githubers.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * TODO - documentar
 */
class GithubersApp : Application(), HasActivityInjector {
    /**
     * TODO - documentar
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        /**
         * TODO - documentar
         */
        AppInjector.init(this)
    }

    /**
     * TODO - documentar
     */
    override fun activityInjector() = dispatchingAndroidInjector

}