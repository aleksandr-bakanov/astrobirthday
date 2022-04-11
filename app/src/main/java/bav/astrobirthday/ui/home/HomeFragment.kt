package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentHomeBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.settings.SettingsViewModel
import bav.astrobirthday.utils.openDatePicker
import bav.astrobirthday.utils.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)

            settingsViewModel.events.observe(viewLifecycleOwner) { events ->
                events.peek { event ->
                    when (event) {
                        is SettingsViewModel.SettingsEvents.OpenPicker -> openDatePicker(
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
                    HomeFragmentDirections.actionNavHomeToPlanetFragment(item.planet.pl_name)
                )
            }
            recyclerView.adapter = adapter

            homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_settings -> {
                        findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavSettings())
                        true
                    }
                    else -> false
                }
            }
        }
    }

}