package bav.astrobirthday.ui.exoplanets

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentExoplanetsBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel.ExoplanetsEvent.ScrollTo
import bav.astrobirthday.utils.setupToolbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExoplanetsFragment :
    BaseFragment<FragmentExoplanetsBinding>(FragmentExoplanetsBinding::inflate) {

    private val viewModel: ExoplanetsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            val exoplanetsAdapter = ExoplanetsAdapter { planetDescription ->
                findNavController().navigate(
                    ExoplanetsFragmentDirections.actionNavExoplanetsToPlanetFragment(
                        planetDescription.planet.planetName
                    )
                )
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.planetsList.collectLatest(exoplanetsAdapter::submitData)
            }
            viewModel.events.observe(viewLifecycleOwner) { events ->
                events.peek { event ->
                    when (event) {
                        is ScrollTo -> recyclerView.scrollToPosition(event.position)
                    }
                    true
                }
            }

            recyclerView.run {
                setHasFixedSize(true)
                adapter = exoplanetsAdapter
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }

            setupSearchView()

            val actionFilter = topAppBar.menu.findItem(R.id.action_sort)
            viewModel.filterIcon.observe(viewLifecycleOwner) {
                actionFilter.setIcon(it)
            }
            actionFilter.setOnMenuItemClickListener {
                findNavController().navigate(
                    ExoplanetsFragmentDirections.actionNavExoplanetsToFilterFragment()
                )
                true
            }
        }
    }

    private fun FragmentExoplanetsBinding.setupSearchView() {
        val searchView = (topAppBar.menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(
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
