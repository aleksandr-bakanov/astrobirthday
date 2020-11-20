package bav.astrobirthday.data.entities

import bav.astrobirthday.common.PlanetType
import java.time.LocalDate

data class PlanetDescription(
    val name: String,
    val ageOnPlanet: Double, // Age in Earth days
    val nearestBirthday: LocalDate,
    val planetType: PlanetType
)