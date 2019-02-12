package br.com.helpdev.githubers.ui.frags.userslist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.repository.NetworkServiceStatus
import br.com.helpdev.githubers.databinding.FragmentUsersListBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.adapter.UserWithFavAdapter
import br.com.helpdev.githubers.worker.GithubUsersWorker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorites_users.*
import retrofit2.HttpException
import java.io.IOException


/**
 * A placeholder fragment containing a simple view.
 */
class UsersListFragment : InjectableBindingFragment<FragmentUsersListBinding, UsersListViewModel>(
    UsersListViewModel::class.java
) {

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentUsersListBinding.inflate(inflater, container, false)

    override fun subscribeUI(
        viewModel: UsersListViewModel,
        binding: FragmentUsersListBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = configureAdapter()

        binding.recyclerView.configure(adapter)


        viewModel.getNetworkServiceStatus().observe(this, Observer {
            binding.isLoading = it.status == NetworkServiceStatus.STATUS_FETCHING

            if (adapter.itemCount <= 0) {
                binding.noDataAndDoNotLoading = !binding.isLoading
            }

            when (it.status) {
                NetworkServiceStatus.STATUS_ERROR -> {
                    if (it.exception is IOException) {
                        showSnackNetworkError(binding.recyclerView)
                    } else {
                        showSnackError(binding.recyclerView, it.exception.toString())
                    }
                }
            }
        })

        viewModel.getUserWithFavList().observe(this, Observer { list ->
            //Atualiza o adapter se conter elementos
            //Update adapter if has items
            list.isNotEmpty().run {
                if (binding.noDataAndDoNotLoading) binding.noDataAndDoNotLoading = false
                adapter.submitList(list)
            }
        })

        binding.fab.setOnClickListener {
            //TODO - Navigate to search user activity
        }
    }

    private fun RecyclerView.configure(adapter: UserWithFavAdapter) {
        this.adapter = adapter
        //Register Recycler context in Fragment (listen click in override onContextItemSelected)->
        registerForContextMenu(this)
    }

    private fun configureAdapter() = UserWithFavAdapter { view, user ->
        view.findNavController().navigate(
            UsersListFragmentDirections.actionUsersListFragmentToUser(user.user.id)
        )
    }

    private fun showSnackNetworkError(view: View) {
        Snackbar.make(
            view, getString(R.string.verify_conection),
            Snackbar.LENGTH_LONG
        ).setActionTextColor(Color.GRAY)
            .setAction(R.string.dismiss) { }.show()
    }

    private fun showSnackError(view: View, message: String) {
        Snackbar.make(
            view, message,
            Snackbar.LENGTH_LONG
        ).setActionTextColor(Color.GRAY)
            .setAction(R.string.dismiss) { }.show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemContext = (recyclerView.adapter as UserWithFavAdapter).itemContext
        when (item.itemId) {
            R.id.add_favorite -> viewModel.addToFavorite(itemContext!!.user.id)
            R.id.remove_favorite -> viewModel.removeFavorite(itemContext!!.user.id)
        }
        return super.onContextItemSelected(item)
    }
}