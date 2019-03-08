package br.com.helpdev.githubers.data.api.github

import br.com.helpdev.githubers.di.module.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

fun getGithubService(): GithubService = AppModule().let { it.provideGithubService(it.provideGson()) }

class GithubServiceTest {
    companion object {
        const val USERNAME = "gbzarelli"
        const val QUERY_SEARCH_USERNAME = "help"
        const val MAX_SIZE_ITEMS_API = 30
    }

    private lateinit var githubService: GithubService
    @ExperimentalCoroutinesApi
    private val cs = CoroutineScope(Unconfined)

    @Before
    fun before() {
        githubService = getGithubService()
    }

    @After
    fun after() {
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getUserWithSuccess() {
        cs.launch {
            with(githubService.getUser(USERNAME).await()) {
                assert(isSuccessful)
                assertNotNull(body())
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getListUserWith30ElementsWithSuccess() {
        cs.launch {
            with(githubService.listUsers().await()) {
                assert(isSuccessful)
                assertNotNull(body())
                assertEquals(body()!!.size, MAX_SIZE_ITEMS_API)
            }
        }
    }


    @Test
    @ExperimentalCoroutinesApi
    fun getRepoWithSuccess() {
        cs.launch {
            with(githubService.getRepos(USERNAME).await()) {
                assert(isSuccessful)
                assertNotNull(body())
                assert(body()!!.isNotEmpty())
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun searchUsersWithSuccess() {
        cs.launch {
            with(githubService.findUsers(QUERY_SEARCH_USERNAME).execute().body()) {
                assertNotNull(this)
                if (null == this) return@launch
                assert(items.isNotEmpty())
                assertEquals(items.size, MAX_SIZE_ITEMS_API)
            }
        }
    }
}