package bav.astrobirthday.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.databinding.FragmentFavoritesBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.utils.setupToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            val adapter = FavoritePlanetsAdapter(requireActivity()) { planetDescription ->
                findNavController().navigate(
                    FavoritesFragmentDirections.actionNavFavoritesToPlanetFragment(planetDescription.planet.planetName)
                )
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.planetsList.collectLatest {
                    adapter.submitList(it)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                emptyListPlaceholder.isVisible = viewModel.countFavorites() == 0
            }

            recyclerView.adapter = adapter
        }
    }

}
