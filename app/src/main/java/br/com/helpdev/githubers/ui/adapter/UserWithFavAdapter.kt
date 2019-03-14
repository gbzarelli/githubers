package br.com.helpdev.githubers.ui.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.db.entity.UserWithFav
import br.com.helpdev.githubers.databinding.ItemUserBinding

class UserWithFavAdapter(
    val onClick: (view: View, user: UserWithFav) -> Unit
) : PagedListAdapter<UserWithFav, ItemUserViewHolder>(UserDiffCallback()) {

    var itemContext: UserWithFav? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemUserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
            )
        )

    override fun onBindViewHolder(holderItemUser: ItemUserViewHolder, position: Int) {
        getItem(position)?.let {
            holderItemUser.bind(it, createOnClickListener(it), createOnLongClickListener(it))
        }
    }

    private fun createOnClickListener(user: UserWithFav) = View.OnClickListener { onClick(it, user) }

    private fun createOnLongClickListener(user: UserWithFav) = View.OnLongClickListener {
        itemContext = user
        false
    }
}

/**
 * Verifica a diferença dos itens para que o adapter saiba das alterações nos items;
 * Verify the items differences for that the adapter know the items modifications;
 */
private class UserDiffCallback : DiffUtil.ItemCallback<UserWithFav>() {
    override fun areItemsTheSame(oldItem: UserWithFav, newItem: UserWithFav) = oldItem.user.id == newItem.user.id
    override fun areContentsTheSame(oldItem: UserWithFav, newItem: UserWithFav) =
        oldItem.user.login == newItem.user.login && oldItem.favorite == newItem.favorite
}

class ItemUserViewHolder(val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserWithFav, listener: View.OnClickListener, listenerLong: View.OnLongClickListener) {
        with(binding) {
            registerContextMenu(user)
            longClickListener = listenerLong
            clickListener = listener
            userFav = user
            executePendingBindings()
        }
    }

    private fun ItemUserBinding.registerContextMenu(user: UserWithFav) {
        this.root.setOnCreateContextMenuListener { menu, v, _ ->
            MenuInflater(v.context).inflate(R.menu.menu_adapter_favorites, menu)
            menu.findItem(user.getFavoriteActionMenuId()).isVisible = false
            menu.setHeaderTitle(user.user.login)
        }
    }
}