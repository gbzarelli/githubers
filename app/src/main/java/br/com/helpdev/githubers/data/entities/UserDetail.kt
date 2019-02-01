package br.com.helpdev.githubers.data.entities

import androidx.room.*
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
"site_admin": false,
"name": "Tom Preston-Werner",
"company": null,
"blog": "http://tom.preston-werner.com",
"location": "San Francisco",
"email": null,
"hireable": null,
"bio": null,
"public_repos": 61,
"public_gists": 62,
"followers": 21333,
"following": 11,
"created_at": "2007-10-20T05:24:19Z",
"updated_at": "2018-12-18T19:52:12Z"
}
 */
@Entity(
    tableName = "user_detail",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"]
    )],
    indices = [Index("user_id","email")]
)
data class UserDetail(
    @PrimaryKey @ColumnInfo(name = "user_id") var id: Int = 0,
    var name: String = "",
    var company: String = "",
    var blog: String = "",
    var location: String = "",
    var email: String = "",
    var hireable: String = "",
    var bio: String = "",
    var public_repos: Int = 0,
    var public_gists: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var created_at: Calendar? = null,
    var updated_at: Calendar? = null
){
    override fun toString(): String {
        return "UserDetail(id=$id, name='$name', company='$company', blog='$blog', location='$location', email='$email', hireable='$hireable', bio='$bio', public_repos=$public_repos, public_gists=$public_gists, followers=$followers, following=$following, created_at=$created_at, updated_at=$updated_at)"
    }
}
