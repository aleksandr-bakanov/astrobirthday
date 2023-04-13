package bav.astrobirthday.utils

import android.content.Context
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.PlanetAndInfoDTO
import bav.astrobirthday.data.entities.toDomain
import bav.astrobirthday.domain.model.PlanetAndInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.ceil
import kotlin.math.floor

fun getAgeOnPlanet(userBirthday: LocalDate, period: Double?): Double? {
    if (period == null) return null
    val now = LocalDate.now()
    val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
    return userAgeInEarthDays / period
}

fun Context.getAgeString(age: Double?): String {
    if (age == null) return resources.getString(R.string.unknown_age)
    val years = floor(age)
    var days = floor(360.0 * (age - years)).toInt()
    val months = floor(days / 30.0).toInt()
    days -= months * 30

    val iYears = years.toLong()
    return when {
        years >= 1.0 -> {
            when {
                months >= 1 && days >= 1 -> getString(
                    R.string.age_string_pattern_three,
                    resources.getQuantityString(R.plurals.years_amount, iYears.toInt(), iYears),
                    resources.getQuantityString(R.plurals.months_amount, months, months),
                    resources.getQuantityString(R.plurals.days_amount, days, days)
                )
                months >= 1 -> getString(
                    R.string.age_string_pattern_two,
                    resources.getQuantityString(R.plurals.years_amount, iYears.toInt(), iYears),
                    resources.getQuantityString(R.plurals.months_amount, months, months)
                )
                else -> getString(
                    R.string.age_string_pattern_one,
                    resources.getQuantityString(R.plurals.years_amount, iYears.toInt(), iYears)
                )
            }
        }
        months >= 1 -> {
            when {
                days >= 1 -> getString(
                    R.string.age_string_pattern_two,
                    resources.getQuantityString(R.plurals.months_amount, months, months),
                    resources.getQuantityString(R.plurals.days_amount, days, days)
                )
                else -> getString(
                    R.string.age_string_pattern_one,
                    resources.getQuantityString(R.plurals.months_amount, months, months)
                )
            }
        }
        else -> getString(
            R.string.age_string_pattern_one,
            resources.getQuantityString(R.plurals.days_amount, days, days)
        )
    }
}

fun Context.getAgeStringShort(age: Double?): String {
    if (age == null) return resources.getString(R.string.unknown_age_short)
    val years = floor(age)
    var days = floor(360.0 * (age - years)).toInt()
    val months = floor(days / 30.0).toInt()
    days -= months * 30
    val iYears = years.toLong()

    return when {
        years >= 1.0 -> resources.getQuantityString(R.plurals.years_amount, iYears.toInt(), iYears)
        months >= 1 -> resources.getQuantityString(R.plurals.months_amount, months, months)
        else -> resources.getQuantityString(R.plurals.days_amount, days, days)
    }
}

val refTextRegex = Regex("<a.+?>(.+?)<")
val refLinkRegex = Regex("href=(.+?) ")

fun getReferenceText(reference: String?): String {
    return refTextRegex.find(reference.orEmpty())?.groupValues?.get(1).orEmpty()
}

fun getReferenceLink(reference: String?): String {
    return refLinkRegex.find(reference.orEmpty())?.groupValues?.get(1).orEmpty()
}

fun getNearestBirthday(userBirthday: LocalDate, name: String, period: Double?): LocalDate? {
    if (period == null) return null
    val now = LocalDate.now()
    if (name == "Earth") {
        val years = ChronoUnit.YEARS.between(userBirthday, now)
        return userBirthday.plusYears(years + 1)
    }
    val userAgeInEarthDays = if (userBirthday == now) {
        period
    } else {
        ChronoUnit.DAYS.between(userBirthday, now).toDouble()
    }
    val daysInNextBirthday = ceil((period * ceil(userAgeInEarthDays / period))).toLong()
    return userBirthday.plusDays(daysInNextBirthday)
}

fun Context.getNearestBirthdayString(date: LocalDate?): String {
    return if (date == null) resources.getString(R.string.unknown_birthday)
    else resources.getString(
        R.string.next_birthday,
        this.localDateToString(date)
    )
}

fun getPlanetType(planetName: String?): PlanetType? {
    return when (planetName) {
        "Mercury" -> PlanetType.MERCURY
        "Venus" -> PlanetType.VENUS
        "Earth" -> PlanetType.EARTH
        "Mars" -> PlanetType.MARS
        "Ceres" -> PlanetType.CERES
        "Jupiter" -> PlanetType.JUPITER
        "Saturn" -> PlanetType.SATURN
        "Uranus" -> PlanetType.URANUS
        "Neptune" -> PlanetType.NEPTUNE
        "Pluto" -> PlanetType.PLUTO
        "Haumea" -> PlanetType.HAUMEA
        "Makemake" -> PlanetType.MAKEMAKE
        "Eris" -> PlanetType.ERIS
        "Sedna" -> PlanetType.SEDNA
        else -> null
    }
}

fun PlanetAndInfoDTO.toDomain(): PlanetAndInfo {
    return PlanetAndInfo( // TODO check defaults
        planet = planet.toDomain(),
        isFavorite = info?.is_favorite ?: false,
        ageOnPlanet = info?.age,
        nearestBirthday = info?.birthday,
        planetType = getPlanetType(planet.pl_name)
    )
}