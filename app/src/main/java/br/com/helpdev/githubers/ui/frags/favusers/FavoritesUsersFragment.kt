package br.com.helpdev.githubers.ui.frags.favusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.helpdev.githubers.databinding.FragmentFavoritesUsersBinding
import br.com.helpdev.githubers.ui.InjectableBindingFragment
import br.com.helpdev.githubers.ui.adapter.UserAdapter

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
        val adapter = UserAdapter { view, user ->
            view.findNavController().navigate(
                FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUser(user.id)
            )
        }

        binding.recyclerView.adapter = adapter

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
}


