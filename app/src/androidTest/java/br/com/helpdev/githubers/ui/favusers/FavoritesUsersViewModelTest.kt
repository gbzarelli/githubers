package br.com.helpdev.githubers.ui.favusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import br.com.helpdev.githubers.data.db.GithubDatabaseTest
import br.com.helpdev.githubers.data.db.dao.UserDaoTest
import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.FavoriteRepository
import br.com.helpdev.githubers.ui.frags.favusers.FavoritesUsersViewModel
import br.com.helpdev.githubers.util.getValue
import org.junit.*

class FavoritesUsersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var githubDatabaseTest: GithubDatabaseTest
    private lateinit var viewModel: FavoritesUsersViewModel
    private lateinit var _user: User

    @Before
    fun before() {
        GithubDatabaseTest().also { githubDatabaseTest = it }.before()
        viewModel = FavoritesUsersViewModel(
            FavoriteRepository(
                githubDatabaseTest.githubDatabase.favoriteDao()
            )
        )
        UserDaoTest().apply {
            userDao = githubDatabaseTest.githubDatabase.userDao()
            loadUserFromJsonToTest()
            _user = user
        }.testInsertNewUserWithSuccess()

        viewModel.addFavorite(_user.id)
    }

    @After
    fun after() {
        githubDatabaseTest.after()
    }

    @Test
    fun mustReturnFavoriteUser() {
        val value: PagedList<UserWithFav> = getValue(viewModel.getFavoriteUsersList())
        val userWithFav = requireNotNull(value[0])
        Assert.assertEquals(userWithFav.user.id, _user.id)
        Assert.assertNotNull(userWithFav.favorite)
        Assert.assertTrue(userWithFav.favorite!! > 0)
    }

    @Test
    fun mustRemoveFavoriteUser() {
        var value: PagedList<UserWithFav> = getValue(viewModel.getFavoriteUsersList())
        val userWithFav = requireNotNull(value[0])
        viewModel.removeFavorite(userWithFav.user.id)
        value = getValue(viewModel.getFavoriteUsersList())
        assert(value.isNullOrEmpty())
    }
}