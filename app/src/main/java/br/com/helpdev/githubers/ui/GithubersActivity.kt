package br.com.helpdev.githubers.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.ActivityGithubersBinding
import br.com.helpdev.githubers.ui.frags.user.UserFragment

/**
 * Main activity. In it is configured the DrawerLayout and navigation of fragments.
 * This class uses androidx's DataBinding and Navigation features.
 *
 * HasSupportFragmentInjector implementation defines this activity as a
 * dependencies for your fragments.

 * @author Guilherme Biff Zarelli
 */
class GithubersActivity : InjectableFragmentActivity() {
    companion object {
        const val INTENT_STRING_USER_LOGIN = "INTENT_STRING_USER_LOGIN"
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityGithubersBinding = DataBindingUtil.setContentView(this, R.layout.activity_githubers)
        configureActivity(binding)
        configureNavigation(binding)
    }

    private fun configureActivity(binding: ActivityGithubersBinding) {
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
    }

    private fun configureNavigation(binding: ActivityGithubersBinding) {
        navController = Navigation.findNavController(this, R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { checkIntentToNavigate(intent) }
    }

    private fun checkIntentToNavigate(intent: Intent) {
        if (intent.hasExtra(INTENT_STRING_USER_LOGIN))
            navigateToUser(intent.getStringExtra(INTENT_STRING_USER_LOGIN))
    }

    private fun navigateToUser(login: String) {
        navController.navigate(R.id.action_to_user, UserFragment.getParamsToNav(login))
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     *         false otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
