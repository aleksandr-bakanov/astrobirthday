package bav.astrobirthday.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.MainActivityViewModel
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.home.HomeFragmentDirections.Companion.actionNavHomeToPlanetFragment
import bav.astrobirthday.ui.settings.DatePickerFragment
import bav.astrobirthday.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: В списке экзопланет справа сделать прокрутку как в контактах
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(top_app_bar)

        mainActivityViewModel.state.observe(viewLifecycleOwner) { state ->
            recycler_view.isVisible = state.barsVisible
            top_app_bar.isVisible = state.barsVisible
            open_settings_button.isVisible = !state.barsVisible
        }

        mainActivityViewModel.events.observe(viewLifecycleOwner) { events ->
            events.peek { event ->
                when (event) {
                    is AnimateBars -> {
                        animateBarsAppearance()
                    }
                }
                true
            }
        }

        open_settings_button.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }

        val adapter = SolarPlanetsAdapter { item ->
            item.planet.pl_name?.let {
                findNavController().navigate(actionNavHomeToPlanetFragment(it))
            }
        }
        recycler_view.adapter = adapter

        homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        top_app_bar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> {
                    findNavController().navigate(R.id.nav_settings)
                    true
                }
                else -> false
            }
        }
    }

    private fun animateBarsAppearance() {
        top_app_bar.isVisible = true
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            top_app_bar,
            "translationY",
            -resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        AnimatorSet().apply {
            playTogether(bottomNavViewAnim)
            duration = 1000L
            interpolator = BounceInterpolator()
            start()
        }
    }

}