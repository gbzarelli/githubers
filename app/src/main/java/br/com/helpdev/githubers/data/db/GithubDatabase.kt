package br.com.helpdev.githubers.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.helpdev.githubers.data.db.dao.FavoriteDao
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.db.dao.UserRepoDao
import br.com.helpdev.githubers.data.entity.FavUser
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserRepo
import br.com.helpdev.githubers.util.DATABASE_VERSION

@Database(
    version = DATABASE_VERSION,
    exportSchema = false,
    entities = [User::class, UserRepo::class, FavUser::class]
)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userRepoDao(): UserRepoDao


    /** All your DAO should be annotated as abstract in this class */
}