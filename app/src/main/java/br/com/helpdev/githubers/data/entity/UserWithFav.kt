package br.com.helpdev.githubers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

class UserWithFav {

    @Embedded
    lateinit var user: User
    @ColumnInfo(name = "f_user_id")
    var favorite: Int? = null

    fun isFavorite() = favorite != null

}
