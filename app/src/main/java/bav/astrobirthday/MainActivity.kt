package bav.astrobirthday

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.ui.common.NavigationController
import bav.astrobirthday.ui.common.peek
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationController {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()

        viewModel.state.observe(this) { state ->
            bottom_nav_view.isVisible = state.barsVisible
        }

        viewModel.events.observe(this) { events ->
            events.peek { event ->
                when (event) {
                    is AnimateBars -> {
                        animateBarsAppearance()
                        false
                    }
                }
            }
        }
    }

    override fun setupToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_exoplanets, R.id.nav_favorites))
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun animateBarsAppearance() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavView.isVisible = true
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            bottomNavView,
            "translationY",
            resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        AnimatorSet().apply {
            playTogether(bottomNavViewAnim)
            duration = 1000L
            interpolator = BounceInterpolator()
            start()
        }
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigation() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController()
        bottomNavView.setupWithNavController(navController)
    }
}