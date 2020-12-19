package bav.astrobirthday

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.common.Preferences
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val preferences: Preferences by inject()
    private var isBirthdayAlreadySetup: Boolean = preferences.userBirthday != null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(if (preferences.userBirthday != null) R.layout.activity_main else R.layout.activity_main_fold)
        setupNavigation()

        preferences.birthdayDate.observe(this, {
            if (it != null && !isBirthdayAlreadySetup) {
                animateBarsAppearance()
                isBirthdayAlreadySetup = true
            }
        })
    }

    private fun animateBarsAppearance() {
        val topAppBar: AppBarLayout = findViewById(R.id.app_bar_layout)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val topAppBarAnim = ObjectAnimator.ofFloat(
            topAppBar,
            "translationY",
            -resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            bottomNavView,
            "translationY",
            resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        AnimatorSet().apply {
            playTogether(topAppBarAnim, bottomNavViewAnim)
            duration = 1000L
            interpolator = BounceInterpolator()
            start()
        }
    }

    private fun setupNavigation() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val topAppBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_exoplanets, R.id.nav_settings))

        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavView.setupWithNavController(navController)
        topAppBar.setupWithNavController(navController, appBarConfiguration)

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> {
                    navController.navigate(R.id.nav_settings)
                    true
                }
                else -> false
            }
        }
    }
}