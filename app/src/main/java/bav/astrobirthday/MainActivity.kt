package bav.astrobirthday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.databinding.ActivityMainBinding
import bav.astrobirthday.ui.common.NavUiConfigurator
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavUiConfigurator {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController()

        lifecycleScope.launch {
            navController.currentBackStackEntryFlow
                .collect { navBackStackEntry ->
                    val isBottomNavVisible = setOf(
                        R.id.nav_setup, R.id.nav_welcome
                    ).contains(navBackStackEntry.destination.id).not()
                    binding.bottomNavView.isVisible = isBottomNavVisible
                }
        }

        setupNavigation()

        enqueuePeriodicBirthdayUpdateWorker(applicationContext)
    }

    override fun setupToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_exoplanets,
                    R.id.nav_favorites,
                    R.id.nav_settings
                )
            )
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigation() {
        binding.bottomNavView.setupWithNavController(navController)
    }

}