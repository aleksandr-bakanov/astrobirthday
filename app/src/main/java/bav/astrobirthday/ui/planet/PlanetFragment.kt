package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.databinding.FragmentPlanetBinding
import bav.astrobirthday.utils.getAgeString
import bav.astrobirthday.utils.getReferenceText
import org.koin.android.viewmodel.ext.android.viewModel

class PlanetFragment : Fragment() {

    private val viewModel: PlanetViewModel by viewModel()
    private val args: PlanetFragmentArgs by navArgs()

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
                planetName.text = it.planet.pl_name
                stellarName.text = it.planet.hostname
                age.text = getAgeString(it.ageOnPlanet, requireContext())
                nearestBirthday.text = it.nearestBirthday.toString()

                planetReferenceText.text = getReferenceText(it.planet.pl_refname)
                planetDiscoveryMethod.text = it.planet.discoverymethod?.name.orEmpty()
                planetDiscoveryYear.text = it.planet.disc_year.toString()
                planetDiscoveryFacility.text = it.planet.disc_facility
                planetOrbitalPeriod.text = it.planet.pl_orbper.toString()
                planetOrbitalSemiMajorAxis.text = it.planet.pl_orbsmax.toString()
                planetRadius.text = it.planet.pl_rade.toString()
                planetMass.text = it.planet.pl_bmasse.toString()
                planetEccentricity.text = it.planet.pl_orbeccen.toString()
                planetEquilibriumTemperature.text = it.planet.pl_eqt.toString()

                stellarReferenceText.text = getReferenceText(it.planet.st_refname)
                stellarSpectralType.text = it.planet.st_spectype
                stellarEffectiveTemperature.text = it.planet.st_teff.toString()
                stellarRaduis.text = it.planet.st_rad.toString()
                stellarMass.text = it.planet.st_mass.toString()

                systemReferenceText.text = getReferenceText(it.planet.sy_refname)
                systemNumberOfStars.text = it.planet.sy_snum.toString()
                systemNumberOfPlanets.text = it.planet.sy_pnum.toString()
                systemDistance.text = it.planet.sy_dist.toString()

                datePlanetReference.text = it.planet.pl_pubdate
            }
        })
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
    }
}