package br.com.helpdev.githubers.ui.userslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.helpdev.githubers.data.api.github.getGithubService
import br.com.helpdev.githubers.data.db.GithubDatabaseTest
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.data.repository.UserRepository
import br.com.helpdev.githubers.ui.frags.userslist.UsersListViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var githubDatabaseTest: GithubDatabaseTest
    private lateinit var viewModel: UsersListViewModel

    @Before
    fun before() {
        GithubDatabaseTest().also { githubDatabaseTest = it }.before()
        viewModel = UsersListViewModel(
            UserRepository(
                githubDatabaseTest.githubDatabase.userDao(),
                getGithubService()
            ),
            FavoriteRepository(
                githubDatabaseTest.githubDatabase.favoriteDao()
            )
        )
    }

    @After
    fun after() {
        githubDatabaseTest.after()
    }

    @Test
    fun x() {
        //TODO - I don't know how testing LiveData with PageList
    }

}