package br.com.helpdev.githubers.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.helpdev.githubers.data.dao.UserDetailsDao
import br.com.helpdev.githubers.data.dao.UserReposDao
import br.com.helpdev.githubers.data.dao.UsersDao
import br.com.helpdev.githubers.data.entities.UserRepo
import br.com.helpdev.githubers.data.entities.User
import br.com.helpdev.githubers.data.entities.UserDetail
import br.com.helpdev.githubers.utilities.DATABASE_NAME
import br.com.helpdev.githubers.utilities.DATABASE_VERSION

@Database(
    version = DATABASE_VERSION,
    exportSchema = false,
    entities = [User::class, UserDetail::class, UserRepo::class]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /** Todos seus DAO devem ser anotados como abstratos nessa clase
     *  All your DAO should be annotated as abstract in this class
     * */
    abstract fun usersDao(): UsersDao
    abstract fun userDetailsDao(): UserDetailsDao
    abstract fun userReposDao(): UserReposDao
    /** ********* */

    companion object {

        // Inicializacao lazy = só é criado quando utilizado.
        // Lazy initialization = is only created when used;
        private val TAG by lazy { AppDatabase::class.java.simpleName }

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreateDatabase")
                        /** Sample to use worker when create db;
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                         */
                    }
                }).build()
        }
    }
}