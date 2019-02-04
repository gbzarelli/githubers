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
import br.com.helpdev.githubers.ui.favrepos.FavoritesReposViewModel
import br.com.helpdev.githubers.util.viewmodel.factory.ViewModelInjectFactory
import javax.inject.Inject

abstract class InjectableBindingFragment<T : ViewDataBinding, Z : ViewModel>
    (private val viewModelClass: Class<out ViewModel>) :
    Fragment(), Injectable {

    @Inject
    lateinit var viewModelInjectFactory: ViewModelInjectFactory

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(inflater, container, savedInstanceState)
        ViewModelProviders.of(this, viewModelInjectFactory).get(viewModelClass).also {
            @Suppress("UNCHECKED_CAST")
            subscribeUI(it as Z, binding, savedInstanceState)
        }
        return binding.root
    }

    abstract fun binding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): T

    abstract fun subscribeUI(viewModel: Z, binding: T, savedInstanceState: Bundle?)
}