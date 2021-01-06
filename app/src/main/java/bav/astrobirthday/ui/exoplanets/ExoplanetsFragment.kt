package bav.astrobirthday.ui.exoplanets

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import bav.astrobirthday.databinding.FragmentExoplanetsBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.exoplanets.ExoplanetsFragmentDirections.Companion.actionNavExoplanetsToPlanetFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExoplanetsFragment : BaseFragment<FragmentExoplanetsBinding>(
    FragmentExoplanetsBinding::inflate
) {

    private val viewModel: ExoplanetsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            val adapter = ExoplanetsAdapter { planetDescription ->
                planetDescription.planet.pl_name?.let {
                    findNavController().navigate(actionNavExoplanetsToPlanetFragment(it))
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.planetsList.collect(adapter::submitData)
            }

            recyclerView.adapter = adapter
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
}
