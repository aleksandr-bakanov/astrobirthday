package bav.astrobirthday.domain.model

import android.os.Parcelable
import bav.astrobirthday.common.DiscoveryMethod
import kotlinx.parcelize.Parcelize

@Parcelize
data class Planet(
    val planetName: String,
    val systemStarNumber: Int? = null,
    val systemPlanetNumber: Int? = null,
    val discoveryMethod: DiscoveryMethod? = null,
    val discoveryYear: Int? = null,
    val discoveryFacility: String? = null,
    val planetBestMassEstimateEarth: Double? = null,
    val planetReference: String? = null,
    val planetOrbitalPeriod: Double? = null,
    val planetOrbitSemiMajorAxis: Double? = null,
    val planetRadiusEarth: Double? = null,
    val planetOrbitEccentricity: Double? = null,
    val planetEquilibriumTemperature: Double? = null,
    val starName: String? = null,
    val starReference: String? = null,
    val starSpectralType: String? = null,
    val starEffectiveTemperature: Double? = null,
    val starRadiusSolar: Double? = null,
    val starMassSolar: Double? = null,
    val systemReference: String? = null,
    val systemDistance: Double? = null
) : Parcelable
