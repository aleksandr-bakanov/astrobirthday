package bav.astrobirthday.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.ExoplanetsAdapter
import kotlinx.android.synthetic.main.fragment_exoplanets.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModel()

    @KoinApiExtension
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExoplanetsAdapter { planetDescription ->
            findNavController().navigate(
                FavoritesFragmentDirections.actionNavFavoritesToPlanetFragment(planetDescription.planet.pl_name.orEmpty())
            )
        }
        viewModel.planetsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.adapter = adapter
    }

}
