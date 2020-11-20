package bav.astrobirthday.ui.home

import androidx.lifecycle.*
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.repository.PlanetRepository
import bav.astrobirthday.utils.Resource
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random

class HomeViewModel(
    private val preferences: Preferences,
    private val planetRepository: PlanetRepository
) : ViewModel() {

    private val _birthdayDate = MutableLiveData<LocalDate?>().apply {
        value = preferences.userBirthday
    }
    val birthdayDate: LiveData<LocalDate?> = _birthdayDate

    val solarPlanets = planetRepository.getPlanets(
        listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")
    ).map { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS ->
                resource.data!!.map {
                    PlanetDescription(
                        name = it.pl_name,
                        ageOnPlanet = getAgeOnPlanet(it.pl_orbper),
                        nearestBirthday = getNearestBirthday(it.pl_orbper),
                        planetType = getPlanetType(it.pl_name)
                    )
                }
            else -> emptyList()
        }
    }

    private fun getAgeOnPlanet(period: Double): Double {
        val now = LocalDate.now()
        val userBirthday = preferences.userBirthday ?: now
        val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
        return userAgeInEarthDays / period
    }

    private fun getNearestBirthday(period: Double): LocalDate {
        val now = LocalDate.now()
        val userBirthday = preferences.userBirthday ?: now
        val userAgeInEarthDays = ChronoUnit.DAYS.between(userBirthday, now).toDouble()
        val daysInNextBirthday = ceil((period * ceil(userAgeInEarthDays / period))).toLong()
        return userBirthday.plusDays(daysInNextBirthday)
    }

    private fun getPlanetType(planetName: String): PlanetType {
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
}