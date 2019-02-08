package br.com.helpdev.githubers.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User

@Dao
interface FavoriteDao{

    @Query("SELECT * FROM fav_user f JOIN user u ON f.user_id = u.user_id")
    fun loadFavorites(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: FavUser)

    @Delete
    fun removeFavorite(favorite: FavUser)
}