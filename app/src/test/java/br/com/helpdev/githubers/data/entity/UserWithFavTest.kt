package br.com.helpdev.githubers.data.entity

import br.com.helpdev.githubers.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class UserWithFavTest {
    companion object {
        private const val ID_FAVORITE_USER = 1
    }

    lateinit var userWithFav: UserWithFav

    @Before
    fun before() {
        userWithFav = UserWithFav()
    }

    @Test
    fun test_ifNeedUpdate_withFail() {
        userWithFav.user = createUser()
        assert(!userWithFav.isNeedUpdate())
    }

    @Test
    fun test_ifNeedUpdate_withSuccess_byRegisterDateTime() {
        userWithFav.user = createUser()
        userWithFav.user.registerDateTime = Calendar.getInstance().apply {
            timeInMillis -= TimeUnit.HOURS.toMillis(1)
        }
        assert(userWithFav.isNeedUpdate())
    }

    @Test
    fun test_ifNeedUpdate_withSuccess_byCreatedDatetime() {
        userWithFav.user = createUser(null)
        assert(userWithFav.isNeedUpdate())
    }

    @Test
    fun verify_ifActionMenuId_isCorrectWithFavoriteUser() {
        userWithFav.favorite = ID_FAVORITE_USER
        Assert.assertEquals(userWithFav.getFavoriteActionMenuId(), R.id.add_favorite)
    }

    @Test
    fun verify_ifActionMenuId_isCorrectWithNonFavoriteUser() {
        userWithFav.favorite = null
        Assert.assertEquals(userWithFav.getFavoriteActionMenuId(), R.id.remove_favorite)
    }

    private fun createUser(createdAt: Calendar? = Calendar.getInstance()): User {
        return User(
            "", 1, "", "", "",
            "", "", "", "", "", "",
            "", "", "", "",
            "", "", false, "", "", "",
            "", "", "", "", 0, 0, 0, 0,
            createdAt, null
        )
    }
}