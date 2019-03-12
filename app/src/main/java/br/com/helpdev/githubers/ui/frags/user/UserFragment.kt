package br.com.helpdev.githubers.ui.frags.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentUserDetailsBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.databinding.observerServiceStatus
import br.com.helpdev.githubers.databinding.setDataReached
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

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

        binding.layoutNetwork.observerServiceStatus(this, viewModel.getNetworkServiceStatus(),
            { viewModel.user.value?.user?.hasLoadDetails() == true },
            {
                if (it is IOException) {
                    showSnackNetworkError(requireContext(), binding.toolbarLayout)
                } else {
                    showSnackError(binding.toolbarLayout, it.toString())
                }
            }
        )

        viewModel.user.observe(this, Observer {
            if (it?.user?.hasLoadDetails() == true) binding.layoutNetwork.setDataReached()
            binding.notifyChange()
        })

        binding.onClickToRepositories = View.OnClickListener {
            it.findNavController().navigate(
                UserFragmentDirections.actionUserToRepoList(UserFragmentArgs.fromBundle(arguments!!).login)
            )
        }

        binding.executePendingBindings()
    }


}
