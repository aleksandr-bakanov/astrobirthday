package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.databinding.FragmentPlanetBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.opengl.PlanetView3d
import bav.astrobirthday.utils.discoveryMethodToStr
import bav.astrobirthday.utils.getAgeString
import bav.astrobirthday.utils.getNearestBirthdayString
import bav.astrobirthday.utils.getReferenceLink
import bav.astrobirthday.utils.getReferenceText
import bav.astrobirthday.utils.openUrl
import bav.astrobirthday.utils.orNa
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import bav.astrobirthday.ui.planet.PlanetItems.Companion.DIFF_CALLBACK
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlanetFragment : BaseFragment<FragmentPlanetBinding>(FragmentPlanetBinding::inflate) {

    private val viewModel: PlanetViewModel by viewModel { parametersOf(args.name) }
    private val args: PlanetFragmentArgs by navArgs()
    private val enlargedView = EnlargedPlanetView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
        setupOpenGL()
    }

    private fun setupOpenGL() {
        with(requireBinding()) {
            val view = PlanetView3d(requireActivity())
            planetView3d.addView(view)
        }
    }

    private fun setupObservers() = with(requireBinding()) {

        val planetDescriptionAdapter = AsyncListDifferDelegationAdapter(
            DIFF_CALLBACK,
            planetDescriptionDividerDelegate(),
            planetDescriptionHeaderDelegate(),
            planetDescriptionTextDelegate(),
            planetDescriptionReferenceDelegate { context?.openUrl(it) }
        )

        viewModel.planet.observe(viewLifecycleOwner) { p ->
            val context = requireContext()

            planetDescriptionAdapter.items = getPlanetItems(p.planet)

            planetName.text = p.planet.pl_name.orNa()
            planetNameCollapsed.text = p.planet.pl_name.orNa()
            age.text = context.getAgeString(p.ageOnPlanet).orNa()
            nearestBirthday.text = context.getNearestBirthdayString(p.nearestBirthday)

            enlargedView.setPlanet(p)
            favoriteButton.setFavorite(p.isFavorite)
            planetAnimation.setData(p)
        }

        recyclerView.run {
            adapter = planetDescriptionAdapter
        }
    }

    private fun getPlanetItems(planet: Planet): List<PlanetItems?> {
        val context = requireContext()
        val list = mutableListOf<PlanetItems>()
        with (planet) {
            if (pl_refname != null || discoverymethod != null || disc_year != null || disc_facility != null ||
                pl_orbper != null || pl_orbsmax != null || pl_rade != null || pl_bmasse != null ||
                pl_orbeccen != null || pl_eqt != null) {
                list.add(PlanetItems.Header(R.string.planet_title))
                pl_refname?.let { list.add(PlanetItems.Reference(getReferenceText(it), getReferenceLink(it))) }
                discoverymethod?.let { list.add(PlanetItems.Text(R.string.discovery_method, context.discoveryMethodToStr(it))) }
                disc_year?.let { list.add(PlanetItems.Text(R.string.year, it.toString())) }
                disc_facility?.let { list.add(PlanetItems.Text(R.string.facility, it)) }
                pl_orbper?.let { list.add(PlanetItems.Text(R.string.orbital_period_days, it.toString())) }
                pl_orbsmax?.let { list.add(PlanetItems.Text(R.string.orbital_semi_major_axis_au, it.toString())) }
                pl_rade?.let { list.add(PlanetItems.Text(R.string.radius_earth_radius, it.toString())) }
                pl_bmasse?.let { list.add(PlanetItems.Text(R.string.mass_earth_mass, it.toString())) }
                pl_orbeccen?.let { list.add(PlanetItems.Text(R.string.orbit_eccentricity, it.toString())) }
                pl_eqt?.let { list.add(PlanetItems.Text(R.string.equilibrium_temperature, it.toString())) }
                }

            if (st_refname != null || st_spectype != null || hostname != null || st_teff != null ||
                st_rad != null || st_mass != null) {
                list.add(PlanetItems.Header(R.string.stellar))
                st_refname?.let { list.add(PlanetItems.Reference(getReferenceText(it), getReferenceLink(it))) }
                st_spectype?.takeUnless { it.isBlank() }?.let { list.add(PlanetItems.Text(R.string.spectral_type, it)) }
                hostname?.let { list.add(PlanetItems.Text(R.string.name, it)) }
                st_teff?.let { list.add(PlanetItems.Text(R.string.effective_temperature, it.toString())) }
                st_rad?.let { list.add(PlanetItems.Text(R.string.radius_solar_radius, it.toString())) }
                st_mass?.let { list.add(PlanetItems.Text(R.string.mass_solar_mass, it.toString())) }
            }

            if (sy_refname != null || sy_snum != null || sy_pnum != null || sy_dist != null) {
                list.add(PlanetItems.Header(R.string.system))
                sy_refname?.let { list.add(PlanetItems.Reference(getReferenceText(it), getReferenceLink(it))) }
                sy_snum?.let { list.add(PlanetItems.Text(R.string.number_of_stars, it.toString())) }
                sy_pnum?.let { list.add(PlanetItems.Text(R.string.number_of_planets, it.toString())) }
                sy_dist?.let { list.add(PlanetItems.Text(R.string.distance_pc, it.toString())) }
            }

            if (pl_pubdate != null || releasedate != null || rowupdate != null) {
                list.add(PlanetItems.Header(R.string.dates))
                pl_pubdate?.let { list.add(PlanetItems.Text(R.string.planetary_reference_publication_date, it)) }
                releasedate?.let { list.add(PlanetItems.Text(R.string.nasa_exoplanet_archive_release_date, it)) }
                rowupdate?.let { list.add(PlanetItems.Text(R.string.last_update_date, it)) }
            }
        }
        return list
    }

    private fun setupClickListeners() = with(requireBinding()) {
        backButton.setOnClickListener { findNavController().navigateUp() }
        favoriteButton.setOnClickListener { viewModel.toggleFavorite() }
        enlargePlanetButton.setOnClickListener {
            enlargedView.show(parentFragmentManager, "enlarged_planet")
        }
    }
}