package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.R
import bav.astrobirthday.common.Preferences
import kotlinx.android.synthetic.main.fragment_planet.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Сделать заебатый coordinator layout
class PlanetFragment : Fragment(R.layout.fragment_planet) {

    private val viewModel: PlanetViewModel by viewModel()
    private val args: PlanetFragmentArgs by navArgs()
    private val preferences: Preferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start(args.name)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.planet.observe(viewLifecycleOwner, Observer {
            preferences.setAppBarTitle(it.name)
            planet_name.text = it.name
            planet_image.setImageResource(it.planetType.imageResId)
            age.text = it.ageOnPlanet.toString()
            nearestBirthday.text = it.nearestBirthday.toString()
        })
    }
}