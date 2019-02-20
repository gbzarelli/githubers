package br.com.helpdev.githubers.ui.frags.userslist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentUsersListBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.adapter.UserWithFavAdapter
import br.com.helpdev.githubers.ui.util.NetworkBindingUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorites_users.*
import java.io.IOException


/**
 * A placeholder fragment containing a simple view.
 */
class UsersListFragment : InjectableBindingFragment<FragmentUsersListBinding, UsersListViewModel>(
    UsersListViewModel::class.java
) {

    override fun onCreateBinding(
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

        NetworkBindingUtil.monitorServiceStatus(this, viewModel.getNetworkServiceStatus(), binding.layoutNetwork,
            { adapter.itemCount > 0 },
            {
                if (it is IOException) {
                    NetworkBindingUtil.showSnackNetworkError(requireContext(), binding.recyclerView)
                } else {
                    NetworkBindingUtil.showSnackError(binding.recyclerView, it.toString())
                }
            }
        )


        viewModel.getUserWithFavList().observe(this, Observer { list ->
            //Atualiza o adapter se conter elementos
            //Update adapter if has items
            list.isNotEmpty().run {
                NetworkBindingUtil.setDataReached(binding.layoutNetwork)
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
            UsersListFragmentDirections.actionUsersListFragmentToUser(user.user.login)
        )
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