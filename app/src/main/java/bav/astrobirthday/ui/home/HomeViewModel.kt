package bav.astrobirthday.ui.home

import androidx.lifecycle.*
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.repository.PlanetRepository
import bav.astrobirthday.utils.Resource
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
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
            Resource.Status.SUCCESS -> {
                val userBirthday = preferences.userBirthday ?: LocalDate.now()
                resource.data!!.map {
                    PlanetDescription(
                        name = it.pl_name,
                        ageOnPlanet = getAgeOnPlanet(userBirthday, it.pl_orbper),
                        nearestBirthday = getNearestBirthday(userBirthday, it.pl_orbper),
                        planetType = getPlanetType(it.pl_name)
                    )
                }
            }
            else -> emptyList()
        }
    }
}