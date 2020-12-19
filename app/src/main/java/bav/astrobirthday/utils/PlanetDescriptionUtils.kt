package bav.astrobirthday.utils

import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.entities.PlanetDescription
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.ceil
import kotlin.math.round
import kotlin.random.Random

fun getAgeOnPlanet(userBirthday: LocalDate, period: Double?): Double {
    if (period == null) return 0.0
    val now = LocalDate.now()
    val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
    return userAgeInEarthDays / period
}

fun getNearestBirthday(userBirthday: LocalDate, period: Double?): LocalDate {
    if (period == null) return LocalDate.MIN
    val now = LocalDate.now()
    val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
    val daysInNextBirthday = ceil((period * ceil(userAgeInEarthDays / period))).toLong()
    return userBirthday.plusDays(daysInNextBirthday)
}

fun getPlanetType(planetName: String?): PlanetType {
    return when (planetName) {
        "Mercury" -> PlanetType.MERCURY
        "Venus" -> PlanetType.VENUS
        "Earth" -> PlanetType.EARTH
        "Mars" -> PlanetType.MARS
        "Jupiter" -> PlanetType.JUPITER
        "Saturn" -> PlanetType.SATURN
        "Uranus" -> PlanetType.URANUS
        "Neptune" -> PlanetType.NEPTUNE
        "Pluto" -> PlanetType.PLUTO
        else -> PlanetType.values()[Random.nextInt(0, PlanetType.values().size)]
    }
}

fun planetToPlanetDescription(planet: Planet?, userBirthday: LocalDate): PlanetDescription {
    return PlanetDescription(
        name = planet?.pl_name.orEmpty(),
        ageOnPlanet = getAgeOnPlanet(userBirthday, planet?.pl_orbper ?: 1.0),
        nearestBirthday = getNearestBirthday(userBirthday, planet?.pl_orbper ?: 1.0),
        planetType = getPlanetType(planet?.pl_name.orEmpty())
    )
}