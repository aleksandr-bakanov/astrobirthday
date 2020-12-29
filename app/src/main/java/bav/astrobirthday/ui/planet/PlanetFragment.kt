package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.R
import bav.astrobirthday.common.CommonUtils
import bav.astrobirthday.databinding.FragmentPlanetBinding
import bav.astrobirthday.utils.getAgeString
import bav.astrobirthday.utils.getReferenceText
import bav.astrobirthday.utils.orNa
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PlanetFragment : Fragment() {

    private val viewModel: PlanetViewModel by viewModel()
    private val args: PlanetFragmentArgs by navArgs()
    private val commonUtils: CommonUtils by inject()

    private var _binding: FragmentPlanetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start(args.name)
        setupObservers()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.planet.observe(viewLifecycleOwner, {
            with(binding) {
                planetName.text = it.planet.pl_name.orNa()
                stellarName.text = it.planet.hostname.orNa()
                age.text = getAgeString(it.ageOnPlanet, requireContext()).orNa()
                nearestBirthday.text = getString(R.string.next_birthday, commonUtils.localDateToString(it.nearestBirthday).orNa())

                planetReferenceText.text = getReferenceText(it.planet.pl_refname).orNa()
                planetDiscoveryMethod.text = it.planet.discoverymethod?.name.orNa()
                planetDiscoveryYear.text = it.planet.disc_year.orNa()
                planetDiscoveryFacility.text = it.planet.disc_facility.orNa()
                planetOrbitalPeriod.text = it.planet.pl_orbper.orNa()
                planetOrbitalSemiMajorAxis.text = it.planet.pl_orbsmax.orNa()
                planetRadius.text = it.planet.pl_rade.orNa()
                planetMass.text = it.planet.pl_bmasse.orNa()
                planetEccentricity.text = it.planet.pl_orbeccen.orNa()
                planetEquilibriumTemperature.text = it.planet.pl_eqt.orNa()

                stellarReferenceText.text = getReferenceText(it.planet.st_refname).orNa()
                stellarSpectralType.text = it.planet.st_spectype.orNa()
                stellarEffectiveTemperature.text = it.planet.st_teff.orNa()
                stellarRaduis.text = it.planet.st_rad.orNa()
                stellarMass.text = it.planet.st_mass.orNa()

                systemReferenceText.text = getReferenceText(it.planet.sy_refname).orNa()
                systemNumberOfStars.text = it.planet.sy_snum.orNa()
                systemNumberOfPlanets.text = it.planet.sy_pnum.orNa()
                systemDistance.text = it.planet.sy_dist.orNa()

                datePlanetReference.text = it.planet.pl_pubdate.orNa()

                favoriteButton.setImageResource(
                    if (it.planet.is_favorite) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                )
            }
        })
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        binding.favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }
}