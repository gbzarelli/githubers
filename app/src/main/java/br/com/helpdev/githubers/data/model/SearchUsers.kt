package br.com.helpdev.githubers.data.model

import br.com.helpdev.githubers.data.entity.User

class SearchUsers(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)