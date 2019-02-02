package br.com.helpdev.githubers.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.helpdev.githubers.data.db.dao.UserDetailDao
import br.com.helpdev.githubers.data.db.dao.UserRepoDao
import br.com.helpdev.githubers.data.db.dao.UserDao
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserDetail
import br.com.helpdev.githubers.data.entity.UserRepo
import br.com.helpdev.githubers.util.DATABASE_VERSION

@Database(
    version = DATABASE_VERSION,
    exportSchema = false,
    entities = [User::class, UserDetail::class, UserRepo::class]
)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {

    /** Todos seus DAO devem ser anotados como abstratos nessa clase
     *  All your DAO should be annotated as abstract in this class
     **/
    abstract fun userDao(): UserDao

    abstract fun userDetailDao(): UserDetailDao
    abstract fun userRepoDao(): UserRepoDao
    /** ********* */

    /**
     * Se não for utilizar DI, utiliza-se o conceito de Singleton :
     *
     *    companion object {
     *
     *        // Inicializacao lazy = só é criado quando utilizado.
     *        // Lazy initialization = is only created when used;
     *        private val TAG by lazy { GithubDatabase::class.java.simpleName }
     *
     *        // For Singleton instantiation
     *        @Volatile private var instance: GithubDatabase? = null
     *
     *        fun getInstance(context: Context): GithubDatabase {
     *            return instance ?: synchronized(this) {
     *                instance ?: buildDatabase(context).also { instance = it }
     *            }
     *        }
     *
     *        // Create and pre-populate the database. See this article for more details:
     *        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
     *        private fun buildDatabase(context: Context): GithubDatabase {
     *            return Room.databaseBuilder(context, GithubDatabase::class.java, DATABASE_NAME)
     *                .addCallback(object : RoomDatabase.Callback() {
     *                    override fun onCreate(db: SupportSQLiteDatabase) {
     *                        super.onCreate(db)
     *                        Log.d(TAG, "onCreateDatabase")
     *                        /** Sample to use worker when create db;
     *                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
     *                        WorkManager.getInstance().enqueue(request)
     *                         */
     *                    }
     *                }).build()
     *        }
     *    }
     */
}