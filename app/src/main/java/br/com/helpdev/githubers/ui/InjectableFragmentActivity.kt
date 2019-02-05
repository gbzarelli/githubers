package br.com.helpdev.githubers.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Activity abstrata que fornece o recurso de suporte para DI de fragments
 * Abstract activity that provides the support feature for DI of fragments
 */
abstract class InjectableFragmentActivity : AppCompatActivity(), HasSupportFragmentInjector {
    /**
     * Cria uma instancia para de AndroidInjector.
     * Responsável em injetar as dependencias mapeadas de forma automatica em seus fragments.
     *
     * Creates an instance of AndroidInjector.
     * Responsible for injecting dependencies mapped automatically into your fragments.
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}