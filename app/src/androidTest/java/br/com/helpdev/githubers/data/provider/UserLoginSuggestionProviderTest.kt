package br.com.helpdev.githubers.data.provider

import android.app.SearchManager
import android.content.ContentResolver
import androidx.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserLoginSuggestionProviderTest {

    companion object {
        const val PATH_TO_SEARCH = "help"
        const val QUERY_PARAMETER_LIMIT_VALUE = "50"
    }

    lateinit var mContentResolver: ContentResolver

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        mContentResolver = context.contentResolver
    }

    @Test
    fun testSearchSuggestWithSuccess_requiredNetworkToFind() {
        val uri = UserLoginSuggestionProvider.URI_SEARCH_SUGGEST_QUERY.buildUpon()
            .appendPath(PATH_TO_SEARCH)
            .appendQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT, QUERY_PARAMETER_LIMIT_VALUE).build()

        val query = mContentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )
        Assert.assertNotNull(query)
        Assert.assertEquals(query!!.count, QUERY_PARAMETER_LIMIT_VALUE.toInt())
        with(query.columnNames) {
            Assert.assertTrue(contains(SearchManager.SUGGEST_COLUMN_TEXT_1))
        }
    }
}