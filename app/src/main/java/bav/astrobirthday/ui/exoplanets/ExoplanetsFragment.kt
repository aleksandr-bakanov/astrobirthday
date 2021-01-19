package bav.astrobirthday.ui.exoplanets

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentExoplanetsBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.exoplanets.ExoplanetsFragmentDirections.Companion.actionNavExoplanetsToPlanetFragment
import bav.astrobirthday.utils.setupToolbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExoplanetsFragment :
    BaseFragment<FragmentExoplanetsBinding>(FragmentExoplanetsBinding::inflate) {

    private val viewModel: ExoplanetsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            val adapter = ExoplanetsAdapter { planetDescription ->
                findNavController().navigate(
                    actionNavExoplanetsToPlanetFragment(planetDescription.planet.pl_name)
                )
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.planetsList.collectLatest(adapter::submitData)
            }

            recyclerView.adapter = adapter
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            (topAppBar.menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.setSearchRequest(query.orEmpty())
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        viewModel.setSearchRequest(query.orEmpty())
                        return true
                    }

                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }
}
