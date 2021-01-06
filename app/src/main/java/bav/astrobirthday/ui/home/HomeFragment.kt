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
import bav.astrobirthday.ui.home.HomeFragmentDirections.Companion.actionNavHomeToPlanetFragment
import bav.astrobirthday.ui.settings.DatePickerFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: В списке экзопланет справа сделать прокрутку как в контактах
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            mainActivityViewModel.state.observe(viewLifecycleOwner) { state ->
                recyclerView.isVisible = state.barsVisible
                topAppBar.isVisible = state.barsVisible
                openSettingsButton.isVisible = !state.barsVisible
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

            openSettingsButton.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(parentFragmentManager, "datePicker")
            }

            val adapter = SolarPlanetsAdapter { item ->
                item.planet.pl_name?.let {
                    findNavController().navigate(actionNavHomeToPlanetFragment(it))
                }
            }
            recyclerView.adapter = adapter

            homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_settings -> {
                        findNavController().navigate(R.id.nav_settings)
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