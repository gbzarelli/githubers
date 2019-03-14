package br.com.helpdev.githubers.data.db.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.helpdev.githubers.data.db.entity.User
import br.com.helpdev.githubers.data.db.entity.UserWithFav


@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Insert(onConflict = REPLACE)
    fun save(user: List<User>)

    @Delete
    fun remove(user: User)

    @Query("SELECT user_id as _id,login as suggest_text_1, login as suggest_intent_data_id from user WHERE login LIKE '%'||:query||'%' limit :limit")
    fun findLoginSuggestion(query: String, limit: Int): Cursor

    @Query("SELECT user_id as _id,login as suggest_text_1 from user WHERE login=:login")
    fun findLoginSuggestion(login: String): Cursor

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun load(userId: Int): LiveData<User>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id WHERE u_.login=:login")
    fun loadWithFav(login: String): LiveData<UserWithFav>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id WHERE u_.login=:login")
    fun loadWithFavInstant(login: String): UserWithFav?

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id ORDER BY u_.user_id")
    fun loadWithFav(): DataSource.Factory<Int, UserWithFav>
}