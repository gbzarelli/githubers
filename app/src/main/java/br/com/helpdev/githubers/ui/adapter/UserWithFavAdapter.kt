package br.com.helpdev.githubers.ui.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.databinding.ItemUserBinding

class UserWithFavAdapter(
    val onClick: (view: View, user: UserWithFav) -> Unit
) : ListAdapter<UserWithFav, UserWithFavAdapter.ViewHolder>(UserDiffCallback()) {

    var itemContext: UserWithFav? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it, createOnClickListener(it), createOnLongClickListener(it))
        }
    }

    private fun createOnClickListener(user: UserWithFav) = View.OnClickListener { onClick(it, user) }

    private fun createOnLongClickListener(user: UserWithFav) = View.OnLongClickListener {
        itemContext = user
        false
    }

    class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserWithFav, listener: View.OnClickListener, listenerLong: View.OnLongClickListener) {
            with(binding) {

                //Registra menu de contexto no click long.
                //Register the context menu on Long Click
                this.root.setOnCreateContextMenuListener { menu, v, _ ->
                    MenuInflater(v.context).inflate(R.menu.menu_adapter_favorites, menu)
                    menu.findItem(
                        if (user.isFavorite()) R.id.add_favorite else R.id.remove_favorite
                    ).isVisible = false
                    menu.setHeaderTitle(user.user.login)
                }

                //Poderia utilizar onContextClickListener porem só para API23 >; Utilizei uma adaptação com longClick;
                //Could be use onContextClickListener but only for API 23 >; I used an adaptation with longClick;
                //-- this.root.setOnContextClickListener {  } --//
                this.longClickListener = listenerLong

                this.clickListener = listener
                this.userFav = user

                executePendingBindings()
            }
        }
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