package bav.astrobirthday.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.databinding.FragmentFavoritesBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.favorites.FavoritesFragmentDirections.Companion.actionNavFavoritesToPlanetFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            val adapter = ExoplanetsAdapter { planetDescription ->
                planetDescription.planet.pl_name?.let {
                    findNavController().navigate(actionNavFavoritesToPlanetFragment(it))
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.planetsList.collect(adapter::submitData)
            }

            recyclerView.adapter = adapter
        }
    }

}
