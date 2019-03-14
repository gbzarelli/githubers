package br.com.helpdev.githubers.ui.frags.favusers

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.adapter.UserWithFavAdapter
import kotlinx.android.synthetic.main.fragment_favorites_users.*


/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : InjectableBindingFragment<FragmentFavoritesUsersBinding, FavoritesUsersViewModel>
    (FavoritesUsersViewModel::class.java) {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentFavoritesUsersBinding.inflate(inflater, container, false)

    override fun subscribeUI(
        viewModel: FavoritesUsersViewModel,
        binding: FragmentFavoritesUsersBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = configureAdapter()

        binding.recyclerView.adapter = adapter
        registerForContextMenu(binding.recyclerView)

        viewModel.getFavoriteUsersList().observe(this, Observer { list ->
            binding.hasItems = list?.isNotEmpty() ?: false
            list?.isNotEmpty().run { adapter.submitList(list) }
        })

        binding.fab.setOnClickListener { onClickListenerFAB(it) }
    }

    private fun onClickListenerFAB(it: View) {
        it.findNavController()
            .navigate(FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUsersList())
    }

    private fun configureAdapter() = UserWithFavAdapter { view, user ->
        view.findNavController().navigate(
            FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUser(user.user.login)
        )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val user = (recyclerView.adapter as UserWithFavAdapter).itemContext ?: return false
        when (item.itemId) {
            R.id.remove_favorite -> onClickRemoveFavorite(user)
        }
        return super.onContextItemSelected(item)
    }

    private fun onClickRemoveFavorite(itemContext: UserWithFav) {
        viewModel.removeFavorite(itemContext.user.id)
    }

}


