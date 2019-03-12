package br.com.helpdev.githubers.ui

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.ActivityGithubersBinding
import br.com.helpdev.githubers.ui.frags.favusers.FavoritesUsersFragmentDirections

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

        /** Make the activity bind with the layout */
        val binding: ActivityGithubersBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_githubers
        )
        drawerLayout = binding.drawerLayout

        /** Make a controller to navigation . Indicates the fragment to layout changes */
        navController = Navigation.findNavController(this, R.id.nav_fragment)

        /**  Make a navigation bar for the toolbar based on drawerLayout and the navigation controller */
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        if (savedInstanceState == null) checkIntentToNavigateToUser()
    }

    private fun checkIntentToNavigateToUser() {
        if (intent?.hasExtra(INTENT_STRING_USER_LOGIN) == false) return
        navigateToUser(intent.getStringExtra(INTENT_STRING_USER_LOGIN))
    }

    private fun navigateToUser(login: String) {
        navController.navigate(
            FavoritesUsersFragmentDirections.actionFavoritesUsersFragmentToUser(login)
        )
    }

    /**
     * Set the back button. Close the DrawerLayout when it's open, else,
     * execute press back action
     */
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
