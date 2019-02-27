package br.com.helpdev.githubers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.Expose
import java.util.*

/**
{
"login": "mojombo",
"id": 1,
"node_id": "MDQ6VXNlcjE=",
"avatar_url": "https://avatars0.githubusercontent.com/u/1?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/mojombo",
"html_url": "https://github.com/mojombo",
"followers_url": "https://api.github.com/users/mojombo/followers",
"following_url": "https://api.github.com/users/mojombo/following{/other_user}",
"gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
"starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
"organizations_url": "https://api.github.com/users/mojombo/orgs",
"repos_url": "https://api.github.com/users/mojombo/repos",
"events_url": "https://api.github.com/users/mojombo/events{/privacy}",
"received_events_url": "https://api.github.com/users/mojombo/received_events",
"type": "User",
"site_admin": false
}
 */
@Entity(
    tableName = "user",
    indices = [
        Index("user_id"),
        Index("login")
    ],
    primaryKeys = ["user_id"]
)
data class User(
    val login: String,
    @ColumnInfo(name = "user_id") val id: Int,
    val node_id: String?,
    val avatar_url: String?,
    val gravatar_id: String?,
    val url: String?,
    val html_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val repos_url: String?,
    val events_url: String?,
    val received_events_url: String?,
    val type: String?,
    val site_admin: Boolean,
    //->Details:
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: String?,
    val bio: String?,
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int,
    val created_at: Calendar? = null,
    val updated_at: Calendar? = null
) {
    @ColumnInfo(name = "register_datetime")
    @Expose(deserialize = false)
    var registerDateTime: Calendar? = GregorianCalendar.getInstance()
        get() = field ?: GregorianCalendar.getInstance().also { field = it }

    fun hasLocation(): Boolean = location?.isNotEmpty() ?: false
    fun hasBio(): Boolean = bio?.isNotEmpty() ?: false
    fun hasName(): Boolean = name?.isNotEmpty() ?: false
    fun hasLoadDetails(): Boolean = created_at != null

    override fun toString(): String {
        return "User(login=$login, id=$id, node_id=$node_id, avatar_url=$avatar_url, gravatar_id=$gravatar_id, url=$url, html_url=$html_url, followers_url=$followers_url, following_url=$following_url, gists_url=$gists_url, starred_url=$starred_url, subscriptions_url=$subscriptions_url, organizations_url=$organizations_url, repos_url=$repos_url, events_url=$events_url, received_events_url=$received_events_url, type=$type, site_admin=$site_admin, name=$name, company=$company, blog=$blog, location=$location, email=$email, hireable=$hireable, bio=$bio, public_repos=$public_repos, public_gists=$public_gists, followers=$followers, following=$following, created_at=$created_at, updated_at=$updated_at)"
    }


}

