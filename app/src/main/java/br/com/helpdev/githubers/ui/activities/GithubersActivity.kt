package br.com.helpdev.githubers.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.databinding.ActivityGithubersBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Atividade principal. Nela é configurado o DrawerLayout e a navegação dos fragments.
 * Esta classe utiliza recursos do DataBinding e Navigation do androidx
 *
 * @author Guilherme Biff Zarelli
 */
class GithubersActivity : AppCompatActivity(), HasSupportFragmentInjector {

    /**
     * TODO - documentar
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Faz a ligação da activity com o layout /
         * Make the activity bind with the layout */
        val binding: ActivityGithubersBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_githubers
        )
        drawerLayout = binding.drawerLayout

        /** Cria um controller para navegação. Indica o fragment para troca de layout
         *  Make a controller to navigation . Indicates the fragment to layout changes
         */
        navController = Navigation.findNavController(this, R.id.nav_fragment)

        /** Cria uma barra de navegação para a toolbar baseada no drawerLayout e o controller de navegação
         *  Make a navigation bar for the toolbar based on drawerLayout and the navigation controller
         * */
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Configura a ActionBar / Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configura a view de navegação / Set up navigation menu
        binding.navigationView.setupWithNavController(navController)

    }

    /**
     * TODO - documentar
     */
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    /**
     * Configura o botão de voltar. Fecha o DrawerLayout se estiver aberto, se não,
     * executa a ação de voltar.
     *
     * Set the back button. Close the DrawerLayout when it's open, else,
     * execute press back action
     *
     * ---
     * Called when the activity has detected the user's press of the back
     * key
     *
     */
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Esse método é chamado sempre que o usuário opta por navegar para Cima na hierarquia de
     * atividades do seu aplicativo na barra de ações.
     *
     * ---
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
