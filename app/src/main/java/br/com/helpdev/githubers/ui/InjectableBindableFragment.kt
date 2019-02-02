package br.com.helpdev.githubers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import br.com.helpdev.githubers.di.Injectable
import br.com.helpdev.githubers.util.viewmodel.factory.ViewModelInjectFactory
import javax.inject.Inject

abstract class InjectableBindableFragment<T : ViewDataBinding> : Fragment(), Injectable {

    @Inject
    lateinit var viewModelInjectFactory: ViewModelInjectFactory

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(inflater, container)
        subscribeUI(binding)
        return binding.root
    }

    abstract fun binding(inflater: LayoutInflater, container: ViewGroup?): T

    abstract fun subscribeUI(binding: T)
}