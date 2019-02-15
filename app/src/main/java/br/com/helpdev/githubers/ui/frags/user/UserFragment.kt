package br.com.helpdev.githubers.ui.frags.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentUserDetailsBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import com.google.android.material.snackbar.Snackbar

/**
 * A placeholder fragment containing a simple view.
 */
class UserFragment : InjectableBindingFragment<FragmentUserDetailsBinding, UserViewModel>
    (UserViewModel::class.java) {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentUserDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@UserFragment//<- I need understand why?!
            setHasOptionsMenu(true)
        }

    override fun onViewModelCreated(viewModel: UserViewModel, savedInstanceState: Bundle?) {
        viewModel.init(UserFragmentArgs.fromBundle(arguments!!).login)
    }

    override fun subscribeUI(
        viewModel: UserViewModel,
        binding: FragmentUserDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        binding.userViewModel = viewModel
        binding.fab.setOnClickListener {
            viewModel.addToFavorite()
            Snackbar.make(binding.fab, R.string.user_add, Snackbar.LENGTH_LONG).show()
        }
        binding.executePendingBindings()
    }


}
