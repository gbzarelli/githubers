package br.com.helpdev.githubers.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(intent) }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doSearchQuery(query)
            }
            intent.getCharSequenceExtra(SearchManager.USER_QUERY)?.also { login ->
                showUser(login.toString())
            }
        }
    }

    private fun showUser(login: String) {
        startActivity(
            Intent(
                this,
                GithubersActivity::class.java
            ).also { it.putExtra(GithubersActivity.INTENT_STRING_USER_LOGIN, login) })
        finish()
    }

    private fun doSearchQuery(query: String) {
        Toast.makeText(this, "doSearchQuery-$query", Toast.LENGTH_LONG).show()
    }
}
