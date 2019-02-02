package br.com.helpdev.githubers

import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserRepo
import br.com.helpdev.githubers.util.gson.GsonFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GsonDeserializarTest {

    private val gson = GsonFactory.getGson()

    @Test
    fun convertJsonUser_toObjectUser_withSuccess() {
        val fromJson = gson.fromJson<User>(JSON_USER, User::class.java)
        checkUser(fromJson)
    }

    private fun checkUser(fromJson: User) {
        assertEquals(fromJson.login, "mojombo")
        assertEquals(fromJson.id, 1)
        assertEquals(fromJson.node_id, "MDQ6VXNlcjE=")
        assertEquals(fromJson.avatar_url, "https://avatars0.githubusercontent.com/u/1?v=4")
        assertEquals(fromJson.url, "https://api.github.com/users/mojombo")
        assertEquals(fromJson.html_url, "https://github.com/mojombo")
        assertEquals(fromJson.gravatar_id, "")
    }

    @Test
    fun convertJsonUserRepo_toObjectUserRepo_withSuccess() {
        val fromJson = GsonFactory.getGson().fromJson<UserRepo>(JSON_USER_REPO_1, UserRepo::class.java)
        assertNotNull(fromJson.owner)
        assertEquals(fromJson.size, 1197)
        assertEquals(fromJson.has_wiki, true)
        fromJson.created_at.let {
            assertNotNull(it!!)
            it.timeZone = TimeZone.getTimeZone("UTC")
            assertEquals(it.get(Calendar.YEAR), 2014)
            assertEquals(it.get(Calendar.MONTH), 10)
            assertEquals(it.get(Calendar.DAY_OF_MONTH), 20)
            assertEquals(it.get(Calendar.HOUR_OF_DAY), 6)
            assertEquals(it.get(Calendar.MINUTE), 42)
            assertEquals(it.get(Calendar.SECOND), 6)
        }
    }
}
