package br.com.helpdev.githubers.ui.frags.userslist

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentUsersListBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.SearchableActivity
import br.com.helpdev.githubers.ui.adapter.UserWithFavAdapter
import br.com.helpdev.githubers.ui.util.observerServiceStatus
import br.com.helpdev.githubers.ui.util.setDataReached
import kotlinx.android.synthetic.main.fragment_favorites_users.*
import java.io.IOException


/**
 * A placeholder fragment containing a simple view.
 */
class UsersListFragment : InjectableBindingFragment<FragmentUsersListBinding, UsersListViewModel>(
    UsersListViewModel::class.java
) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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

        binding.layoutNetwork.observerServiceStatus(this, viewModel.getNetworkServiceStatus(),
            { adapter.itemCount > 0 },
            {
                if (it is IOException) {
                    showSnackNetworkError(requireContext(), binding.recyclerView)
                } else {
                    showSnackError(binding.recyclerView, it.toString())
                }
            }
        )

        viewModel.getUserWithFavList().observe(this, Observer { list ->
            //Atualiza o adapter se conter elementos
            //Update adapter if has items
            list.isNotEmpty().run {
                binding.layoutNetwork.setDataReached()
                adapter.submitList(list)
            }
        })

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView.setSearchableInfo(
            searchManager!!.getSearchableInfo(
                ComponentName(requireContext(), SearchableActivity::class.java)
            )
        )
        searchView.setIconifiedByDefault(false)

        super.onCreateOptionsMenu(menu, inflater)
    }
}