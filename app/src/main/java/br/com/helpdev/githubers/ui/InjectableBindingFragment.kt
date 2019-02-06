package br.com.helpdev.githubers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import br.com.helpdev.githubers.di.Injectable
import br.com.helpdev.githubers.di.util.ViewModelInjectFactory
import br.com.helpdev.githubers.ui.frags.favusers.FavoritesUsersFragment
import javax.inject.Inject

/**
 * Fragment abstrato que realiza o auto-binding com a view e já com suporte a DI.
 * Também abstrai o carregamento do ViewModel.
 *
 * Abstract fragment that performs auto-binding with the view and already with DI support.
 * Also abstracts the loading of the ViewModel.
 */
abstract class InjectableBindingFragment<T : ViewDataBinding, Z : ViewModel>
    (private val viewModelClass: Class<out ViewModel>) : Fragment(), Injectable {

    companion object {
        internal val TAG by lazy { FavoritesUsersFragment::class.java.simpleName }
    }

    @Inject
    lateinit var viewModelInjectFactory: ViewModelInjectFactory

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(inflater, container, savedInstanceState)
        /** Load ViewModel and call subscribeUI */
        ViewModelProviders.of(this, viewModelInjectFactory).get(viewModelClass).also {
            @Suppress("UNCHECKED_CAST") subscribeUI(it as Z, binding, savedInstanceState)
        }
        return binding.root
    }

    abstract fun binding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): T

    abstract fun subscribeUI(viewModel: Z, binding: T, savedInstanceState: Bundle?)
}