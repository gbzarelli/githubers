package br.com.helpdev.githubers.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import br.com.helpdev.githubers.BuildConfig
import br.com.helpdev.githubers.data.db.dao.UserDao
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.content.UriMatcher
import android.app.SearchManager


class UserLoginSuggestionProvider : ContentProvider() {

    companion object {
        private const val PATH = "loginsuggestion"
        private const val AUTHORITY = BuildConfig.APPLICATION_ID + ".$PATH.provider"

        private const val TYPE_ALL_SUGGESTIONS = 1
        private const val TYPE_SINGLE_SUGGESTION = 2
    }

    @Inject
    lateinit var userDao: UserDao

    lateinit var mUriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        AndroidInjection.inject(this)
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "/#", TYPE_SINGLE_SUGGESTION)
        mUriMatcher.addURI(AUTHORITY, "search_suggest_query/*", TYPE_ALL_SUGGESTIONS)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        if (mUriMatcher.match(uri) == TYPE_ALL_SUGGESTIONS) {
            val query = uri.lastPathSegment!!.toLowerCase()
            val limit = uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT)!!.toInt()
            return userDao.findLoginSuggestion(query, limit)
        } else if (mUriMatcher.match(uri) == TYPE_SINGLE_SUGGESTION) {
            return userDao.findLoginSuggestion(uri.lastPathSegment!!.toInt())
        }

        return null
    }


    override fun insert(uri: Uri, values: ContentValues?) = throw UnsupportedOperationException("Not yet implemented")

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun getType(uri: Uri) = throw UnsupportedOperationException("Not yet implemented")


}