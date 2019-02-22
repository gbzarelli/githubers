package br.com.helpdev.githubers.data.db

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test

open class GithubDatabaseTest {

    lateinit var githubDatabase: GithubDatabase

    @Before
    open fun before() {
        createDB()
    }

    private fun createDB() {
        val context = InstrumentationRegistry.getTargetContext()
        githubDatabase = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java).build()
        onDBCreated(githubDatabase)
    }

    @After
    open fun after() {
        closeDB()
    }

    private fun closeDB() {
        githubDatabase.close()
    }

    @Test
    fun testDatabaseLoadedWithSuccess() {
        assert(githubDatabase.isOpen)
    }

    open fun onDBCreated(githubDatabase: GithubDatabase) {}

}