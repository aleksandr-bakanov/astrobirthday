package bav.astrobirthday.data.entities

import android.os.Parcelable
import bav.astrobirthday.common.PlanetType
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class PlanetDescription(
    val planet: Planet,
    val isFavorite: Boolean,
    val ageOnPlanet: Double? = null, // Age in Earth days
    val nearestBirthday: LocalDate? = null,
    val planetType: PlanetType? = null,
    val neighbours: List<Planet> = emptyList()
) : Parcelable