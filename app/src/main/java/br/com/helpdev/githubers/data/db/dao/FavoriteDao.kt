package br.com.helpdev.githubers.data.db.dao

import androidx.paging.DataSource
import androidx.room.*
import br.com.helpdev.githubers.data.db.entity.FavUser
import br.com.helpdev.githubers.data.db.entity.UserWithFav

@Dao
interface FavoriteDao {

    @Query("SELECT u.*,f.user_id f_user_id FROM fav_user f JOIN user u ON f.user_id = u.user_id")
    fun loadFavorites(): DataSource.Factory<Int, UserWithFav>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: FavUser)

    @Delete
    fun removeFavorite(favorite: FavUser)
}