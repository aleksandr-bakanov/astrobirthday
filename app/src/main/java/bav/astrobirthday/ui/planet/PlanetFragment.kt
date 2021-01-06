package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentPlanetBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlanetFragment : BaseFragment<FragmentPlanetBinding>(FragmentPlanetBinding::inflate) {

    private val viewModel: PlanetViewModel by viewModel { parametersOf(args.name) }
    private val args: PlanetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() = with(requireBinding()) {
        viewModel.planet.observe(viewLifecycleOwner) { p ->
            val context = requireContext()
            planetName.text = p.planet.pl_name.orNa()
            stellarName.text = p.planet.hostname.orNa()
            age.text = context.getAgeString(p.ageOnPlanet).orNa()
            nearestBirthday.text = getString(
                R.string.next_birthday,
                context.localDateToString(p.nearestBirthday).orNa()
            )

            planetReferenceText.text = getReferenceText(p.planet.pl_refname).orNa()
            planetDiscoveryMethod.text =
                context.discoveryMethodToStr(p.planet.discoverymethod)
            planetDiscoveryYear.text = p.planet.disc_year.orNa()
            planetDiscoveryFacility.text = p.planet.disc_facility.orNa()
            planetOrbitalPeriod.text = p.planet.pl_orbper.orNa()
            planetOrbitalSemiMajorAxis.text = p.planet.pl_orbsmax.orNa()
            planetRadius.text = p.planet.pl_rade.orNa()
            planetMass.text = p.planet.pl_bmasse.orNa()
            planetEccentricity.text = p.planet.pl_orbeccen.orNa()
            planetEquilibriumTemperature.text = p.planet.pl_eqt.orNa()

            stellarReferenceText.text = getReferenceText(p.planet.st_refname).orNa()
            stellarSpectralType.text = p.planet.st_spectype.orNa()
            stellarEffectiveTemperature.text = p.planet.st_teff.orNa()
            stellarRaduis.text = p.planet.st_rad.orNa()
            stellarMass.text = p.planet.st_mass.orNa()

            systemReferenceText.text = getReferenceText(p.planet.sy_refname).orNa()
            systemNumberOfStars.text = p.planet.sy_snum.orNa()
            systemNumberOfPlanets.text = p.planet.sy_pnum.orNa()
            systemDistance.text = p.planet.sy_dist.orNa()

            datePlanetReference.text = p.planet.pl_pubdate.orNa()

            favoriteButton.setFavorite(p.planet.is_favorite)

            getReferenceLink(p.planet.pl_refname)?.let { url ->
                planetReferenceButton.isVisible = true
                planetReferenceButton.setOnClickListener { context.openUrl(url) }
            }
            getReferenceLink(p.planet.st_refname)?.let { url ->
                stellarReferenceButton.isVisible = true
                stellarReferenceButton.setOnClickListener { context.openUrl(url) }
            }
            getReferenceLink(p.planet.sy_refname)?.let { url ->
                systemReferenceButton.isVisible = true
                systemReferenceButton.setOnClickListener { context.openUrl(url) }
            }
        }
    }

    private fun setupClickListeners() = with(requireBinding()) {
        backButton.setOnClickListener { findNavController().popBackStack() }
        favoriteButton.setOnClickListener { viewModel.toggleFavorite() }
    }
}