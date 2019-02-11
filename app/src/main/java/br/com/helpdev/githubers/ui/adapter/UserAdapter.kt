package br.com.helpdev.githubers.ui.adapter

import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.databinding.ItemUserBinding

class UserAdapter(
    private val resIdMenuContext: Int? = null,
    val onClick: (view: View, user: User) -> Unit
) : ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {

    var itemContext: User? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
            ), resIdMenuContext
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it, createOnClickListener(it), createOnLongClickListener(it))
        }
    }

    private fun createOnClickListener(user: User) = View.OnClickListener { onClick(it, user) }
    private fun createOnLongClickListener(user: User) = View.OnLongClickListener {
        itemContext = user
        false
    }

    class ViewHolder(val binding: ItemUserBinding, private val resIdMenuContext: Int?) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, listener: View.OnClickListener, listenerLong: View.OnLongClickListener) {
            with(binding) {

                //Registra menu de contexto no click long.
                if (null != resIdMenuContext) {
                    this.root.setOnCreateContextMenuListener { menu, v, _ ->
                        MenuInflater(v.context).inflate(resIdMenuContext, menu)
                        menu.setHeaderTitle(user.login)
                    }
                }

                //Poderia utilizar onContextClickListener porem só para API23>; Utilizei uma adaptação com longClick
                //this.root.setOnContextClickListener {  }

                this.longClickListener = listenerLong

                this.clickListener = listener
                this.user = user
                executePendingBindings()
            }
        }
    }


}


private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem.login == newItem.login
}