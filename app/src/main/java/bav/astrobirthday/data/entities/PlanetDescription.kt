package bav.astrobirthday.data.entities

import bav.astrobirthday.common.PlanetType
import java.time.LocalDate

data class PlanetDescription(
    val planet: Planet,
    val isFavorite: Boolean,
    val ageOnPlanet: Double = 0.0, // Age in Earth days
    val nearestBirthday: LocalDate = LocalDate.now(),
    val planetType: PlanetType? = null,
    val neighbours: List<Planet> = emptyList()
)