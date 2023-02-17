package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentPlanetBinding
import bav.astrobirthday.domain.model.Planet
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.opengl.PlanetView3d
import bav.astrobirthday.ui.planet.PlanetItems.Companion.DIFF_CALLBACK
import bav.astrobirthday.utils.discoveryMethodToStr
import bav.astrobirthday.utils.getAgeString
import bav.astrobirthday.utils.getNearestBirthdayString
import bav.astrobirthday.utils.getReferenceLink
import bav.astrobirthday.utils.getReferenceText
import bav.astrobirthday.utils.openUrl
import bav.astrobirthday.utils.orNa
import com.google.android.material.tabs.TabLayout
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlanetFragment : BaseFragment<FragmentPlanetBinding>(FragmentPlanetBinding::inflate) {

    private val viewModel: PlanetViewModel by viewModel { parametersOf(args.name) }
    private val args: PlanetFragmentArgs by navArgs()
    private var planetAndInfo: PlanetAndInfo? = null
    private var view3d: PlanetView3d? = null

    enum class TabCategory(val value: String) {
        Planet("Planet"),
        Stellar("Stellar"),
        System("System")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    private fun setupOpenGL(p: PlanetAndInfo) {
        with(requireBinding()) {
            if (planetView3d.childCount == 0) {
                val view = PlanetView3d(requireActivity(), p)
                view3d = view
                planetView3d.removeAllViews()
                planetView3d.addView(view)
            }
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

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                planetAndInfo?.let {
                    planetDescriptionAdapter.items = getPlanetItems(
                        it.planet,
                        when (tab?.text) {
                            "Planet" -> TabCategory.Planet
                            "Stellar" -> TabCategory.Stellar
                            "System" -> TabCategory.System
                            else -> TabCategory.Planet
                        }
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {}

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                Log.d("cqhg43", "cqhg43: onTransitionChange progress = $progress")
                view3d?.setZoom(progress)
            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {}
            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })

        viewModel.planet.observe(viewLifecycleOwner) { p ->
            val context = requireContext()
            planetAndInfo = p

            setupOpenGL(p)

            planetDescriptionAdapter.items = getPlanetItems(p.planet, TabCategory.Planet)

            planetName.text = p.planet.getPlanetName(context)
            age.text = context.getAgeString(p.ageOnPlanet).orNa()
            nearestBirthday.text = context.getNearestBirthdayString(p.nearestBirthday)

            favoriteButton.setFavorite(p.isFavorite)
            planetAnimation.setData(p)
        }

        recyclerView.run {
            adapter = planetDescriptionAdapter
        }
    }

    private fun getPlanetItems(planet: Planet, category: TabCategory): List<PlanetItems?> {
        val context = requireContext()
        val list = mutableListOf<PlanetItems>()
        with(planet) {
            when (category) {
                TabCategory.Planet -> {
                    planetReference?.let {
                        list.add(
                            PlanetItems.Reference(
                                getReferenceText(it),
                                getReferenceLink(it)
                            )
                        )
                    }
                    discoveryMethod?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.discovery_method,
                                context.discoveryMethodToStr(it)
                            )
                        )
                    }
                    discoveryYear?.let { list.add(PlanetItems.Text(R.string.year, it.toString())) }
                    discoveryFacility?.let { list.add(PlanetItems.Text(R.string.facility, it)) }
                    planetOrbitalPeriod?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.orbital_period_days,
                                it.toString()
                            )
                        )
                    }
                    planetOrbitSemiMajorAxis?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.orbital_semi_major_axis_au,
                                it.toString()
                            )
                        )
                    }
                    planetRadiusEarth?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.radius_earth_radius,
                                it.toString()
                            )
                        )
                    }
                    planetBestMassEstimateEarth?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.mass_earth_mass,
                                it.toString()
                            )
                        )
                    }
                    planetOrbitEccentricity?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.orbit_eccentricity,
                                it.toString()
                            )
                        )
                    }
                    planetEquilibriumTemperature?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.equilibrium_temperature,
                                it.toString()
                            )
                        )
                    }
                }
                TabCategory.Stellar -> {
                    starReference?.let {
                        list.add(
                            PlanetItems.Reference(
                                getReferenceText(it),
                                getReferenceLink(it)
                            )
                        )
                    }
                    starSpectralType?.takeUnless { it.isBlank() }
                        ?.let { list.add(PlanetItems.Text(R.string.spectral_type, it)) }
                    starName?.let { list.add(PlanetItems.Text(R.string.name, it)) }
                    starEffectiveTemperature?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.effective_temperature,
                                it.toString()
                            )
                        )
                    }
                    starRadiusSolar?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.radius_solar_radius,
                                it.toString()
                            )
                        )
                    }
                    starMassSolar?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.mass_solar_mass,
                                it.toString()
                            )
                        )
                    }
                }
                TabCategory.System -> {
                    systemReference?.let {
                        list.add(
                            PlanetItems.Reference(
                                getReferenceText(it),
                                getReferenceLink(it)
                            )
                        )
                    }
                    systemStarNumber?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.number_of_stars,
                                it.toString()
                            )
                        )
                    }
                    systemPlanetNumber?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.number_of_planets,
                                it.toString()
                            )
                        )
                    }
                    systemDistance?.let {
                        list.add(
                            PlanetItems.Text(
                                R.string.distance_pc,
                                it.toString()
                            )
                        )
                    }
                }
            }
        }
        return list
    }

    private fun setupClickListeners() = with(requireBinding()) {
        backButton.setOnClickListener { findNavController().navigateUp() }
        favoriteButton.setOnClickListener { viewModel.toggleFavorite() }
    }

}