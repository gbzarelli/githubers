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
import br.com.helpdev.githubers.databinding.observerServiceStatus
import br.com.helpdev.githubers.databinding.setDataReached
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
        val adapter = createAdapter()

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

    /**
     * Configura o RecyclerView.
     * - Define o adapter
     * - Registra o contextMenu nele.
     *
     * Configure the RecyclerView.
     *  - Define the adapter
     *  - Register the contextMenu in them
     */
    private fun RecyclerView.configure(adapter: UserWithFavAdapter) {
        this.adapter = adapter
        //Register Recycler context in Fragment (listen click in override onContextItemSelected)->
        registerForContextMenu(this)
    }


    /**
     * Create the UserWithFavAdapter
     */
    private fun createAdapter() = UserWithFavAdapter { view, user ->
        view.findNavController().navigate(
            UsersListFragmentDirections.actionUsersListFragmentToUser(user.user.login)
        )
    }

    /**
     * Configura o click do contextMenu.
     * - Atualmente está tratando o contextMenu criado pelo RecyclerView.
     *
     * Configure the contextMenu's click
     * - Is currently handling the contextMenu created by RecyclerView
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemContext = (recyclerView.adapter as UserWithFavAdapter).itemContext
        when (item.itemId) {
            R.id.add_favorite -> viewModel.addToFavorite(itemContext!!.user.id)
            R.id.remove_favorite -> viewModel.removeFavorite(itemContext!!.user.id)
        }
        return super.onContextItemSelected(item)
    }

    /**
     * Create menu Search.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager?

        /**
         * Define as informações de busca.
         * Aparentemente o Android busca o searchableInfo do ComponentName que foi passado como parametro.
         * Esse SearchableInfo foi definido no Manifest da Activity passada pelo ComponentName:
         *
         * Define the search information.
         * Apparently the Android finds the searchableInfo of the ComponentName that was passed as parameter
         *
         *      <intent-filter>
         *          <action android:name="android.intent.action.SEARCH"/>
         *       </intent-filter>
         *       <meta-data android:name="android.app.searchable"
         *       android:resource="@xml/searchable"/>
         */
        searchView.setSearchableInfo(
            searchManager!!.getSearchableInfo(
                ComponentName(requireContext(), SearchableActivity::class.java)
            )
        )
        searchView.setIconifiedByDefault(false)

        super.onCreateOptionsMenu(menu, inflater)
    }
}