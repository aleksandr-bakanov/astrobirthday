package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.MainViewModel
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentHomeBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.utils.setupToolbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            if (mainViewModel.isBirthdaySet().not()) {
                findNavController().navigate(R.id.nav_setup)
            }
        }

        with(requireBinding()) {
            setupToolbar(topAppBar)

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