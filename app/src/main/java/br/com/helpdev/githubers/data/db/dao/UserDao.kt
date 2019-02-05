package br.com.helpdev.githubers.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User


@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Insert(onConflict = REPLACE)
    fun save(user: List<User>)

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun load(userId: Int): LiveData<User>

    @Query("SELECT * FROM user")
    fun load(): LiveData<List<User>>

    /**
     * TODO - extract to another DAO ->
     */

    @Query("SELECT * FROM fav_user f JOIN user u ON f.user_id = u.user_id")
    fun loadFavorites(): LiveData<List<User>>

    @Insert(onConflict = IGNORE)
    fun insertFavorite(favorite: FavUser)

    @Delete
    fun removeFavorite(favorite: FavUser)

}