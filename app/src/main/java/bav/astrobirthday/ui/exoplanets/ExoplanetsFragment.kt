package bav.astrobirthday.ui.exoplanets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.adapter.ExoplanetsAdapter
import bav.astrobirthday.ui.exoplanets.ExoplanetsFragmentDirections.Companion.actionNavExoplanetsToPlanetFragment
import kotlinx.android.synthetic.main.fragment_exoplanets.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class ExoplanetsFragment : Fragment(R.layout.fragment_exoplanets) {

    private val viewModel: ExoplanetsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
