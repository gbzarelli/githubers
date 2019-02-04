package br.com.helpdev.githubers.data.db.dao

import androidx.room.Dao
import br.com.helpdev.githubers.data.entity.User
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query


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

}