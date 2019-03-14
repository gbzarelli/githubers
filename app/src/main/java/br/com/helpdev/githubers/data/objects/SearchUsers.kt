package br.com.helpdev.githubers.data.objects

import br.com.helpdev.githubers.data.db.entity.User

class SearchUsers(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)