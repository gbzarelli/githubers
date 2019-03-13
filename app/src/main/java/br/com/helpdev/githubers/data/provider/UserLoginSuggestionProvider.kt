package br.com.helpdev.githubers.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import br.com.helpdev.githubers.BuildConfig
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.content.UriMatcher
import android.app.SearchManager
import br.com.helpdev.githubers.data.repository.UserRepository


class UserLoginSuggestionProvider : ContentProvider() {

    companion object {
        private const val PATH_SEARCH_SUGGEST_QUERY = "search_suggest_query"
        private const val AUTHORITY = BuildConfig.APPLICATION_ID + ".loginsuggestion.provider"

        val URI_SEARCH_SUGGEST_QUERY = Uri.parse("content://$AUTHORITY/$PATH_SEARCH_SUGGEST_QUERY/")!!

        private const val TYPE_ALL_SUGGESTIONS = 1
        private const val TYPE_SINGLE_SUGGESTION = 2

        private var mUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "/#", TYPE_SINGLE_SUGGESTION)
            addURI(AUTHORITY, "$PATH_SEARCH_SUGGEST_QUERY/*", TYPE_ALL_SUGGESTIONS)
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(): Boolean {
        AndroidInjection.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (mUriMatcher.match(uri)) {
            TYPE_ALL_SUGGESTIONS -> {
                findLoginSuggestions(uri)
            }
            TYPE_SINGLE_SUGGESTION -> {
                findLoginSuggestionByLogin(uri.lastPathSegment!!)
            }
            else -> null
        }
    }

    private fun findLoginSuggestionByLogin(login: String) = userRepository.findLoginSuggestionsSynchronous(login)

    private fun findLoginSuggestions(uri: Uri): Cursor? {
        val query = uri.lastPathSegment!!.toLowerCase()
        val limit = uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT)!!.toInt()
        return userRepository.findLoginSuggestionsSynchronous(query, limit)
    }


    override fun insert(uri: Uri, values: ContentValues?) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun getType(uri: Uri) =
        throw UnsupportedOperationException("Not yet implemented")


}