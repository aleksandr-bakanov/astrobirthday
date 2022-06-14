package bav.astrobirthday.domain.model

import java.time.LocalDate

data class PlanetUserInfo(
    val name: String,
    val isFavorite: Boolean,
    val ageOnPlanet: Double? = null,
    val nearestBirthday: LocalDate? = null
)