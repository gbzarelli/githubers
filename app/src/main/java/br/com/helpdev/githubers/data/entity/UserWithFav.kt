package br.com.helpdev.githubers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import br.com.helpdev.githubers.R

class UserWithFav {

    @Embedded
    lateinit var user: User
    @ColumnInfo(name = "f_user_id")
    var favorite: Int? = null

    fun isFavorite() = favorite != null

    fun getFavoriteActionMenuId() = if (isFavorite()) R.id.add_favorite else R.id.remove_favorite
}
