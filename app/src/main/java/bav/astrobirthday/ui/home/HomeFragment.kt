package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.databinding.FragmentHomeBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.utils.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)

            val adapter = SolarPlanetsAdapter { item ->
                findNavController().navigate(
                    HomeFragmentDirections.actionNavHomeToPlanetFragment(item.planet.planetName)
                )
            }
            recyclerView.adapter = adapter

            homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

}