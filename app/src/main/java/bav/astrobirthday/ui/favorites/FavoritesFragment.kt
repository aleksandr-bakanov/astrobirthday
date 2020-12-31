package bav.astrobirthday.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.favorites.FavoritesFragmentDirections.Companion.actionNavFavoritesToPlanetFragment
import kotlinx.android.synthetic.main.fragment_exoplanets.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
