package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ComposeFragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                HomeScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is HomeViewModel.Event.GoToPlanet ->
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavHomeToPlanetFragment(event.planetName)
                    )
                else -> Unit
            }
        }
    }

}