package br.com.helpdev.githubers.ui.frags.user

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentUserDetailsBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.databinding.observerServiceStatus
import br.com.helpdev.githubers.databinding.setDataReached
import br.com.helpdev.githubers.util.GITHUB_BASE_URL
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

/**
 * A placeholder fragment containing a simple view.
 */
class UserFragment : InjectableBindingFragment<FragmentUserDetailsBinding, UserViewModel>
    (UserViewModel::class.java) {
    companion object {
        private val TYPE_TEXT_PLAIN by lazy { "text/plain" }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentUserDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@UserFragment//<- I don't understand why?!
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
        binding.onClickToRepositories = View.OnClickListener { navigateToRepoList(it) }
        binding.fab.setOnClickListener { onClickListenerFAB() }

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

        binding.executePendingBindings()
    }


    private fun onClickListenerFAB() {
        viewModel.addToFavorite()
        Snackbar.make(binding.fab, R.string.user_add, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToRepoList(it: View) {
        it.findNavController().navigate(
            UserFragmentDirections.actionUserToRepoList(getLoginFromArguments())
        )
    }

    private fun getLoginFromArguments() = UserFragmentArgs.fromBundle(arguments!!).login

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_share -> {
                onShareSelected()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onShareSelected(): Boolean {
        startActivity(buildShareIntent())
        return true
    }

    private fun buildShareIntent(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getShareLink())
            .setType(TYPE_TEXT_PLAIN)
            .createChooserIntent()
            .apply {
                //https://android-developers.googleblog.com/2012/02/share-with-intents.html
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // If we're on Lollipop, we can open the intent as a document
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                } else {
                    // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                }
            }
    }

    private fun getShareLink(): String {
        return "$GITHUB_BASE_URL/${getLoginFromArguments()}"
    }

}
