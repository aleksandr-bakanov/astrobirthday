package bav.astrobirthday.utils

import android.content.Context
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.entities.PlanetDescription
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.random.Random

fun getAgeOnPlanet(userBirthday: LocalDate, period: Double?): Double {
    if (period == null) return 0.0
    val now = LocalDate.now()
    val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
    return userAgeInEarthDays / period
}

fun getAgeString(age: Double, context: Context): String {
    val years = floor(age)
    var days = floor(360.0 * (age - years)).toInt()
    val months = floor(days / 30.0).toInt()
    days -= months * 30

    val iYears = years.toInt()
    return when {
        years >= 1.0 -> {
            when {
                months >= 1 && days >= 1 -> context.getString(
                    R.string.age_string_pattern_three,
                    context.resources.getQuantityString(R.plurals.years_amount, iYears, iYears),
                    context.resources.getQuantityString(R.plurals.months_amount, months, months),
                    context.resources.getQuantityString(R.plurals.days_amount, days, days)
                )
                months >= 1 -> context.getString(
                    R.string.age_string_pattern_two,
                    context.resources.getQuantityString(R.plurals.years_amount, iYears, iYears),
                    context.resources.getQuantityString(R.plurals.months_amount, months, months)
                )
                else -> context.getString(
                    R.string.age_string_pattern_one,
                    context.resources.getQuantityString(R.plurals.years_amount, iYears, iYears)
                )
            }
        }
        months >= 1 -> {
            when {
                days >= 1 -> context.getString(
                    R.string.age_string_pattern_two,
                    context.resources.getQuantityString(R.plurals.months_amount, months, months),
                    context.resources.getQuantityString(R.plurals.days_amount, days, days)
                )
                else -> context.getString(
                    R.string.age_string_pattern_one,
                    context.resources.getQuantityString(R.plurals.months_amount, months, months)
                )
            }
        }
        else -> context.getString(
            R.string.age_string_pattern_one,
            context.resources.getQuantityString(R.plurals.days_amount, days, days)
        )
    }
}

fun getAgeStringShort(age: Double, context: Context): String {
    val years = floor(age)
    var days = floor(360.0 * (age - years)).toInt()
    val months = floor(days / 30.0).toInt()
    days -= months * 30
    val iYears = years.toInt()

    return when {
        years >= 1.0 -> context.resources.getQuantityString(R.plurals.years_amount, iYears, iYears)
        months >= 1 -> context.resources.getQuantityString(R.plurals.months_amount, months, months)
        else -> context.resources.getQuantityString(R.plurals.days_amount, days, days)
    }
}

fun getAgeStringForMainScreen(age: Double, context: Context): String {
    val years = floor(age)
    val days = floor(360.0 * (age - years))
    val months = floor(days / 30.0)
    return when {
        years >= 1.0 -> {
            context.getString(R.string.age_string_pattern_years_short, years.toInt())
        }
        months >= 1.0 -> {
            context.getString(R.string.age_string_pattern_months_short, months.toInt())
        }
        else -> {
            context.getString(R.string.age_string_pattern_days_short, days.toInt())
        }
    }
}

val refTextRegex = Regex("<a.+?>(.+?)<")
val refLinkRegex = Regex("href=(.+?) ")

fun getReferenceText(reference: String?): String {
    return refTextRegex.find(reference.orEmpty())?.groupValues?.get(1).orEmpty()
}

fun getReferenceLink(reference: String?): String? {
    return refLinkRegex.find(reference.orEmpty())?.groupValues?.get(1)
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
        planet = planet!!,
        ageOnPlanet = getAgeOnPlanet(userBirthday, planet?.pl_orbper ?: 1.0),
        nearestBirthday = getNearestBirthday(userBirthday, planet?.pl_orbper ?: 1.0),
        planetType = getPlanetType(planet?.pl_name.orEmpty())
    )
}