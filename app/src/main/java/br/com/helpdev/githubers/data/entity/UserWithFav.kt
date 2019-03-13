package br.com.helpdev.githubers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.util.isLessThanOneHour


class UserWithFav {

    @Embedded
    lateinit var user: User
    @ColumnInfo(name = "f_user_id")
    var favorite: Int? = null

    fun isFavorite() = favorite != null

    fun getFavoriteActionMenuId() = if (isFavorite()) R.id.add_favorite else R.id.remove_favorite

    fun isNeedUpdate(): Boolean {
        user.created_at?.let {
            if (isLessThanOneHour(user.registerDateTime?.timeInMillis ?: 0L)) {
                return false
            }
        }
        return true
    }

}
