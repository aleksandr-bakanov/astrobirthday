package bav.astrobirthday.ui.planet

import androidx.lifecycle.*
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.repository.PlanetRepository
import bav.astrobirthday.utils.Resource
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
import java.time.LocalDate

class PlanetViewModel(
    private val preferences: Preferences,
    private val planetRepository: PlanetRepository
) : ViewModel() {

    private val _planetName = MutableLiveData<String>()

    private val _planet = _planetName.switchMap { name ->
        planetRepository.getPlanet(name)
    }.map { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                val userBirthday = preferences.userBirthday ?: LocalDate.now()
                val planet = resource.data as Planet
                PlanetDescription(
                    name = planet.pl_name,
                    ageOnPlanet = getAgeOnPlanet(userBirthday, planet.pl_orbper),
                    nearestBirthday = getNearestBirthday(userBirthday, planet.pl_orbper),
                    planetType = getPlanetType(planet.pl_name)
                )
            }
            else -> PlanetDescription()
        }
    }

    val planet: LiveData<PlanetDescription> = _planet

    fun start(name: String) {
        _planetName.value = name
    }
}