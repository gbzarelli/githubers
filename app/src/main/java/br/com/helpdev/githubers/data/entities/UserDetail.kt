package br.com.helpdev.githubers.data.entities

import androidx.room.*
import java.util.*

/**
{
"login": "1",
"id": 1825798,
"node_id": "MDQ6VXNlcjE4MjU3OTg=",
"avatar_url": "https://avatars2.githubusercontent.com/u/1825798?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/1",
"html_url": "https://github.com/1",
"followers_url": "https://api.github.com/users/1/followers",
"following_url": "https://api.github.com/users/1/following{/other_user}",
"gists_url": "https://api.github.com/users/1/gists{/gist_id}",
"starred_url": "https://api.github.com/users/1/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/1/subscriptions",
"organizations_url": "https://api.github.com/users/1/orgs",
"repos_url": "https://api.github.com/users/1/repos",
"events_url": "https://api.github.com/users/1/events{/privacy}",
"received_events_url": "https://api.github.com/users/1/received_events",
"type": "User",
"site_admin": false,
<!-- informacoes abaixo referentes a consulta completa -->
"name": "Michael",
"company": null,
"blog": "",
"location": "San Francisco, CA",
"email": null,
"hireable": null,
"bio": null,
"public_repos": 3,
"public_gists": 0,
"followers": 15,
"following": 0,
"created_at": "2012-06-07T06:10:07Z",
"updated_at": "2019-01-17T08:29:21Z"
}
 */
@Entity(
    tableName = "user_detail",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"]
    )],
    indices = [Index("id")]
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
)
