package bav.astrobirthday.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : ComposeFragment() {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                FavoritesScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is FavoritesViewModel.Event.GoToPlanet ->
                    findNavController().navigate(
                        FavoritesFragmentDirections.actionNavFavoritesToPlanetFragment(event.planetName)
                    )
                else -> Unit
            }
        }
    }

}
