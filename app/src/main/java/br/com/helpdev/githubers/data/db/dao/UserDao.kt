package br.com.helpdev.githubers.data.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
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

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun load(userId: Int): LiveData<User>

    @Query("SELECT * FROM user")
    fun load(): DataSource.Factory<Int, User>

    @Query("SELECT u_.*, f_.user_id f_user_id FROM user u_ left join fav_user f_ ON u_.user_id = f_.user_id ORDER BY u_.user_id")
    fun loadWithFav(): DataSource.Factory<Int, UserWithFav>
}