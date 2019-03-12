package br.com.helpdev.githubers.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Abstract activity that provides the support feature for DI of fragments
 */
abstract class InjectableFragmentActivity : AppCompatActivity(), HasSupportFragmentInjector {

    /**
     * Responsible for injecting dependencies mapped automatically into your fragments.
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}