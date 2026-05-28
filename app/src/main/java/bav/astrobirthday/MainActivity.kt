package bav.astrobirthday

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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
        setupInsets()

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

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
                topMargin = insets.top
            }

            // Return CONSUMED if you don't want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }

}