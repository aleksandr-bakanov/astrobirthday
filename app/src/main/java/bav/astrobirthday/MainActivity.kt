package bav.astrobirthday

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.databinding.ActivityMainBinding
import bav.astrobirthday.ui.common.peek
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        viewModel.state.observe(this) { state ->
            binding.bottomNavView.isVisible = state.barsVisible
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

    private fun animateBarsAppearance() = with(binding) {
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

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)
    }
}