package br.com.helpdev.githubers.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BrowsableActivity : AppCompatActivity() {
    companion object {
        private val TAG by lazy { BrowsableActivity::class.java.simpleName }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_VIEW == intent.action) processIntentAction(intent)
        finish()
    }

    private fun processIntentAction(intent: Intent) {
        val expectedUserLogin = intent.data?.let { getExpectedUserLogin(it) }
        if (expectedUserLogin.isNullOrEmpty()) {
            openGithubers()
        } else {
            openUser(expectedUserLogin)
        }
    }

    private fun getExpectedUserLogin(uri: Uri): String? {
        return uri.pathSegments?.let {
            if (it.isEmpty()) null else it[0]
        }
    }

    private fun openGithubers() {
        startActivity(Intent(this, GithubersActivity::class.java))
    }

    private fun openUser(login: String) {
        startActivity(Intent(this, GithubersActivity::class.java)
            .also {
                it.putExtra(GithubersActivity.INTENT_STRING_USER_LOGIN, login)
            })
    }

}