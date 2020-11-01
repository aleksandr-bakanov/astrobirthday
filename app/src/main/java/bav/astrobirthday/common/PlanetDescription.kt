package bav.astrobirthday.common

import java.time.LocalDate

data class PlanetDescription(
    val name: String,
    val ageOnPlanet: Int, // Age in Earth days
    val nearestBirthday: LocalDate,

    val planetType: PlanetType
)