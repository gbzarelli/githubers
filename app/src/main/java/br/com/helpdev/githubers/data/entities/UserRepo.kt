package br.com.helpdev.githubers.data.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "repo",
    indices = [Index("repo_id", "owner_user_id", "owner_login")],
    primaryKeys = ["repo_id"]
)
data class UserRepo(
    @ColumnInfo(name = "repo_id") val id: Int,
    @ColumnInfo(name = "repo_node_id") val node_id: String?,
    @ColumnInfo(name = "repo_html_url") val html_url: String?,
    @ColumnInfo(name = "repo_url") val url: String?,
    @ColumnInfo(name = "repo_events_url") val events_url: String?,
    @Embedded(prefix = "owner_") val owner: Owner?,
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
    data class Owner(
        @ColumnInfo(name = "user_id") val id: Int,
        val login: String
    )
}

