package br.com.helpdev.githubers.ui.frags.favusers

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_favorites_users.*

/**
 * A placeholder fragment containing a simple view.
 */
class FavoritesUsersFragment : InjectableBindingFragment<FragmentFavoritesUsersBinding, FavoritesUsersViewModel>
    (FavoritesUsersViewModel::class.java) {

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentFavoritesUsersBinding.inflate(inflater, container, false)

    override fun subscribeUI(
        viewModel: FavoritesUsersViewModel,
        binding: FragmentFavoritesUsersBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = UserAdapter(R.menu.menu_adapter_favorites) { view, user ->
            view.findNavController().navigate(
                FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUser(user.id)
            )
        }

        with(binding.recyclerView) {
            this.adapter = adapter
            registerForContextMenu(this)
        }

        viewModel.getFavoriteUsersList().observe(this, Observer { list ->
            //Verifica se contém itens para adicionar a variavel de layout.
            //Verify if has items to add the layout variable
            binding.hasItems = list?.isNotEmpty() ?: false

            //Atualiza o adapter se conter elementos
            //Update adapter if has items
            list?.isNotEmpty().run { adapter.submitList(list) }
        })


        /**
         * Ao clicar no FAB realiza a navegação para a UsersListFragment
         * Clicking the FAB navigates to the UsersListFragment
         */
        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUsersList())
        }
    }

    /**
     * TODO SAMPLE
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemContext = (recyclerView.adapter as UserAdapter).itemContext
        Toast.makeText(context, "PAH - ${itemContext!!.login}", Toast.LENGTH_SHORT).show()
        return super.onContextItemSelected(item)

    }

}


