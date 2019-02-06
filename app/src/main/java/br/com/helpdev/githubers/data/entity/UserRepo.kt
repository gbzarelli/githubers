package br.com.helpdev.githubers.data.entity

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "repo",
    primaryKeys = ["repo_id"],
    indices = [
        Index("repo_id"),
        Index("owner_user_id"),
        Index("owner_login")
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["owner_user_id"]
        )]
)
data class UserRepo(
    @ColumnInfo(name = "repo_id") val id: Int,
    @ColumnInfo(name = "repo_node_id") val node_id: String?,
    @ColumnInfo(name = "repo_html_url") val html_url: String?,
    @ColumnInfo(name = "repo_url") val url: String?,
    @ColumnInfo(name = "repo_events_url") val events_url: String?,
    @Embedded(prefix = "owner_") val owner: Owner,
    val name: String?,
    val full_name: String?,

    val created_at: Calendar?,
    val updated_at: Calendar?,
    val pushed_at: Calendar?,

    @SerializedName("private") val repo_private: Boolean,
    val size: Int,
    val stargazers_count: Int,
    val watchers_count: Int,
    val fork: Boolean,
    val has_issues: Boolean,
    val has_projects: Boolean,
    val has_downloads: Boolean,
    val has_wiki: Boolean,
    val has_pages: Boolean,
    val archived: Boolean,
    val forks_count: Int,
    val open_issues_count: Int,
    val forks: Int,
    val open_issues: Int,
    val watchers: Int,

    val description: String?,
    val forks_url: String?,
    val keys_url: String?,
    val collaborators_url: String?,
    val teams_url: String?,
    val hooks_url: String?,
    val issue_events_url: String?,
    val assignees_url: String?,
    val branches_url: String?,
    val tags_url: String?,
    val blobs_url: String?,
    val git_tags_url: String?,
    val git_refs_url: String?,
    val trees_url: String?,
    val statuses_url: String?,
    val languages_url: String?,
    val stargazers_url: String?,
    val contributors_url: String?,
    val subscribers_url: String?,
    val subscription_url: String?,
    val commits_url: String?,
    val git_commits_url: String?,
    val comments_url: String?,
    val issue_comment_url: String?,
    val contents_url: String?,
    val compare_url: String?,
    val merges_url: String?,
    val downloads_url: String?,
    val issues_url: String?,
    val pulls_url: String?,
    val milestones_url: String?,
    val notifications_url: String?,
    val labels_url: String?,
    val releases_url: String?,
    val deployments_url: String?,
    val git_url: String?,
    val ssh_url: String?,
    val clone_url: String?,
    val svn_url: String?,
    val homepage: String?,
    val mirror_url: String?,
    val language: String?,
    val license: String?,
    val default_branch: String?

) {
    @ColumnInfo(name = "register_datetime")
    @Expose(deserialize = false)
    var registerDateTime: Calendar? = GregorianCalendar.getInstance()
        get() = field ?: GregorianCalendar.getInstance().also { field = it }


    data class Owner(
        @ColumnInfo(name = "user_id") val id: Int,
        val login: String
    ) {
        override fun toString(): String {
            return "Owner(id=$id, login='$login')"
        }
    }

    override fun toString(): String {
        return "UserRepo(registerDateTime=${registerDateTime?.time
            ?: "null"}, id=$id, node_id=$node_id, html_url=$html_url, url=$url, events_url=$events_url, owner=$owner, name=$name, full_name=$full_name, created_at=$created_at, updated_at=$updated_at, pushed_at=$pushed_at, repo_private=$repo_private, size=$size, stargazers_count=$stargazers_count, watchers_count=$watchers_count, fork=$fork, has_issues=$has_issues, has_projects=$has_projects, has_downloads=$has_downloads, has_wiki=$has_wiki, has_pages=$has_pages, archived=$archived, forks_count=$forks_count, open_issues_count=$open_issues_count, forks=$forks, open_issues=$open_issues, watchers=$watchers, description=$description, forks_url=$forks_url, keys_url=$keys_url, collaborators_url=$collaborators_url, teams_url=$teams_url, hooks_url=$hooks_url, issue_events_url=$issue_events_url, assignees_url=$assignees_url, branches_url=$branches_url, tags_url=$tags_url, blobs_url=$blobs_url, git_tags_url=$git_tags_url, git_refs_url=$git_refs_url, trees_url=$trees_url, statuses_url=$statuses_url, languages_url=$languages_url, stargazers_url=$stargazers_url, contributors_url=$contributors_url, subscribers_url=$subscribers_url, subscription_url=$subscription_url, commits_url=$commits_url, git_commits_url=$git_commits_url, comments_url=$comments_url, issue_comment_url=$issue_comment_url, contents_url=$contents_url, compare_url=$compare_url, merges_url=$merges_url, downloads_url=$downloads_url, issues_url=$issues_url, pulls_url=$pulls_url, milestones_url=$milestones_url, notifications_url=$notifications_url, labels_url=$labels_url, releases_url=$releases_url, deployments_url=$deployments_url, git_url=$git_url, ssh_url=$ssh_url, clone_url=$clone_url, svn_url=$svn_url, homepage=$homepage, mirror_url=$mirror_url, language=$language, license=$license, default_branch=$default_branch)"
    }


}

