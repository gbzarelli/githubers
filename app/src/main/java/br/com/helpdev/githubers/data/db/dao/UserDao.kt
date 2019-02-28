package br.com.helpdev.githubers.data.db.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserWithFav


@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Insert(onConflict = REPLACE)
    fun save(user: List<User>)

    @Delete
    fun remove(user: User)

    @Query("SELECT user_id,login,user_id from user WHERE login LIKE '%'||:query||'%' limit :limit")
    fun findLoginSuggestion(query: String, limit:Int): Cursor

    @Query("SELECT user_id,login,user_id from user WHERE user_id=:userId")
    fun findLoginSuggestion(userId: Int): Cursor


    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun load(userId: Int): LiveData<User>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id WHERE u_.login=:login")
    fun loadWithFav(login: String): LiveData<UserWithFav>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id WHERE u_.login=:login")
    fun loadWithFavInstant(login: String): UserWithFav?

    @Query("SELECT * FROM user")
    fun load(): DataSource.Factory<Int, User>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id ORDER BY u_.user_id")
    fun loadWithFav(): DataSource.Factory<Int, UserWithFav>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id where u_.login like :query OR u_.name like :query OR u_.email like :query ORDER BY u_.user_id")
    fun findWithFav(query: String): DataSource.Factory<Int, UserWithFav>
}