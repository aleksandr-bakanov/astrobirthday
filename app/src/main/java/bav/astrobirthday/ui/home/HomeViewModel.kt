package bav.astrobirthday.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.repository.PlanetRepository
import bav.astrobirthday.utils.Resource
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
import java.time.LocalDate

class HomeViewModel(
    private val preferences: Preferences,
    private val planetRepository: PlanetRepository
) : ViewModel() {

    val solarPlanets = planetRepository.getPlanets(
        listOf(
            "Mercury",
            "Venus",
            "Earth",
            "Mars",
            "Jupiter",
            "Saturn",
            "Uranus",
            "Neptune",
            "Pluto"
        )
    ).map { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                val userBirthday = preferences.userBirthday ?: LocalDate.now()
                resource.data!!.map {
                    PlanetDescription(
                        name = it.pl_name.orEmpty(),
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