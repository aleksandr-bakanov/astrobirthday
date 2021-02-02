package bav.astrobirthday.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.MainActivityViewModel
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentHomeBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.home.HomeFragmentDirections.Companion.actionNavHomeToNavSettings
import bav.astrobirthday.ui.home.HomeFragmentDirections.Companion.actionNavHomeToPlanetFragment
import bav.astrobirthday.ui.settings.SettingsViewModel
import bav.astrobirthday.utils.openDatePicker
import bav.astrobirthday.utils.setupToolbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            mainActivityViewModel.state.observe(viewLifecycleOwner) { state ->
                recyclerView.isVisible = state.barsVisible
                topAppBar.isVisible = state.barsVisible
                setBirthday.isVisible = !state.barsVisible
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

            settingsViewModel.events.observe(viewLifecycleOwner) { events ->
                events.peek { event ->
                    when (event) {
                        is SettingsViewModel.PickerEvent.OpenPicker -> openDatePicker(
                            millis = event.millis,
                            onDateSelected = settingsViewModel::onDateSelected
                        )
                    }
                    true
                }
            }

            setBirthday.setOnClickListener {
                settingsViewModel.pickBirthday()
            }

            val adapter = SolarPlanetsAdapter { item ->
                findNavController().navigate(
                    actionNavHomeToPlanetFragment(item.planet.pl_name)
                )
            }
            recyclerView.adapter = adapter

            homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_settings -> {
                        findNavController().navigate(actionNavHomeToNavSettings())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun animateBarsAppearance() = with(requireBinding()) {
        topAppBar.isVisible = true
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            topAppBar,
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