package br.com.helpdev.githubers.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.di.Injectable
import br.com.helpdev.githubers.di.viewmodel.ViewModelInjectorFactory
import br.com.helpdev.githubers.ui.frags.favusers.FavoritesUsersFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Fragment abstrato que realiza o auto-onCreateBinding com a view e já com suporte a DI.
 * Também abstrai o carregamento do ViewModel.
 *
 * Abstract fragment that performs auto-onCreateBinding with the view and already with DI support.
 * Also abstracts the loading of the ViewModel.
 */
abstract class InjectableBindingFragment<T : ViewDataBinding, Z : ViewModel>
    (private val viewModelClass: Class<out ViewModel>) : Fragment(), Injectable {

    companion object {
        internal val TAG by lazy { FavoritesUsersFragment::class.java.simpleName }
    }

    @Inject
    lateinit var viewModelInjectorFactory: ViewModelInjectorFactory

    lateinit var binding: T
    lateinit var viewModel: Z


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UNCHECKED_CAST")
        with(ViewModelProviders.of(this, viewModelInjectorFactory).get(viewModelClass) as Z) {
            viewModel = this
            onViewModelCreated(this, savedInstanceState)
        }

        return onCreateBinding(inflater, container, savedInstanceState).apply {
            binding = this
            subscribeUI(viewModel, this, savedInstanceState)
        }.root
    }

    open fun onViewModelCreated(viewModel: Z, savedInstanceState: Bundle?) {}

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): T

    abstract fun subscribeUI(viewModel: Z, binding: T, savedInstanceState: Bundle?)

}