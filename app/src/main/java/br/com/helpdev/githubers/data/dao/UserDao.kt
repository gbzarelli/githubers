package br.com.helpdev.githubers.data.dao

import androidx.room.Dao
import br.com.helpdev.githubers.data.entities.User
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query


@Dao
interface UserDao{

    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun load(userId: Int): LiveData<User>

}